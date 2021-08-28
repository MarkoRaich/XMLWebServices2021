package ftn.xws.contentservice.service.impl;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import ftn.xws.contentservice.dto.AlbumDTO;
import ftn.xws.contentservice.dto.AllCommentDTO;
import ftn.xws.contentservice.dto.AllReactionsDTO;
import ftn.xws.contentservice.dto.CommentDTO;
import ftn.xws.contentservice.dto.PostDTO;
import ftn.xws.contentservice.dto.RatingDTO;
import ftn.xws.contentservice.dto.ReactionsNumberDTO;
import ftn.xws.contentservice.dto.UsernameDTO;
import ftn.xws.contentservice.enumeration.RatingType;
import ftn.xws.contentservice.exception.PostDoesNotExistException;
import ftn.xws.contentservice.exception.ProfileBlockedException;
import ftn.xws.contentservice.exception.ProfilePrivateException;
import ftn.xws.contentservice.model.Comment;
import ftn.xws.contentservice.model.Media;
import ftn.xws.contentservice.model.Post;
import ftn.xws.contentservice.model.Rating;
import ftn.xws.contentservice.model.Story;
import ftn.xws.contentservice.repository.CommentRepository;
import ftn.xws.contentservice.repository.MediaRepository;
import ftn.xws.contentservice.repository.PostRepository;
import ftn.xws.contentservice.repository.RatingRepository;
import ftn.xws.contentservice.repository.StoryRepository;
import ftn.xws.contentservice.service.MediaService;
import ftn.xws.contentservice.service.UserService;

@Service
public class MediaServiceImpl implements MediaService{
	
	private MediaRepository mediaRepository;
	private PostRepository postRepository;
	private StoryRepository storyRepository;
	private CommentRepository commentRepository;
	private RatingRepository ratingRepository;
	
	@Autowired
	private UserService userService;
	
	@Value("${media.storage}")
	private String storageDirectoryPath;

	@Autowired
    public MediaServiceImpl(MediaRepository mediaRepository, PostRepository postRepository, StoryRepository storyRepository,
    		CommentRepository commentRepository, RatingRepository ratingRepository) {
        this.mediaRepository = mediaRepository;
        this.postRepository = postRepository;
        this.storyRepository = storyRepository;
        this.commentRepository = commentRepository;
        this.ratingRepository = ratingRepository;
	}

	@Override
	public void createPost(MultipartFile file, PostDTO postDTO, String username) throws IOException {
		
		String fileName = saveFile(file, storageDirectoryPath);
		String fileDownloadUri = "media/content/" + fileName;
		System.out.println(fileDownloadUri);

		Media media = new Media();
		
		UsernameDTO usernameDTO = new UsernameDTO();
		usernameDTO.setUsername(username);
		
		ResponseEntity<String> response =  userService.getAccountName(usernameDTO);
		String accountName = response.getBody();
		
		media.setUsername(accountName);
		Set<String> paths = new HashSet<String>();
		paths.add(fileDownloadUri);
		media.setPath(paths);
		media.setCreated(LocalDateTime.now());
		mediaRepository.save(media);
		
		Post post = new Post();
		post.setDescription(postDTO.getDescription());
		post.setLocation(postDTO.getLocation());
		Set<String> tags = new HashSet<>(postDTO.getTags());
		post.setTags(tags);
		post.setMedia(media);
		postRepository.save(post);

		/*List<String> followers = profileService.getFollowers(media.getUsername());
		for(String follower : followers) {
			sendNotification(NotificationType.POST, follower, media.getUsername(), Long.toString(post.getId()));
		}*/
		
	}

	@Override
	public Media save(Media media) {
		Media newMedia = mediaRepository.save(media);
		return newMedia;
	}
	
	@Override
	public void createStory(MultipartFile file, boolean closeFriends, String username) throws IOException {
		String fileName = saveFile(file, storageDirectoryPath);
		String fileDownloadUri = "media/content/" + fileName;

		System.out.println(fileDownloadUri);

		Media media = new Media();
		
		UsernameDTO usernameDTO = new UsernameDTO();
		usernameDTO.setUsername(username);
		
		ResponseEntity<String> response =  userService.getAccountName(usernameDTO);
		String accountName = response.getBody();
		
		media.setUsername(accountName);
		Set<String> paths = new HashSet<String>();
		paths.add(fileDownloadUri);
		media.setPath(paths);
		media.setCreated(LocalDateTime.now());
		mediaRepository.save(media);
		
		Story story = new Story();
		story.setDateCreated(LocalDateTime.now());
		story.setCloseFriends(closeFriends);
		story.setHighlighted(false);
		story.setMedia(media);
		storyRepository.save(story);

//		List<String> followers;
//		if(story.isCloseFriends())
//			followers = userService.getCloseFriends(media.getUsername());
//		else
//			followers = userService.getFollowers(media.getUsername());
//		for(String follower : followers) {
//			System.out.println(follower);
//			//	sendNotification(NotificationType.STORY, follower, media.getUsername(), media.getUsername());
//		}
	}
	
	private String saveFile(MultipartFile file, String storageDirectoryPath) throws IOException {
		String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
		String extension = getFileExtension(originalFileName);
		String fileName = UUID.randomUUID().toString() + "." + extension;

		System.out.println(fileName);

		Path storageDirectory = Paths.get(storageDirectoryPath);
		if(!Files.exists(storageDirectory)){
			Files.createDirectories(storageDirectory);
		}
		Path destination = Paths.get(storageDirectory.toString() + File.separator + fileName);
		Files.copy(file.getInputStream(), destination, StandardCopyOption.REPLACE_EXISTING);
		return fileName;
	}
	
	private String getFileExtension(String fileName) throws IOException {
		String[] parts = fileName.split("\\.");
		if(parts.length > 0)
			return parts[parts.length - 1];
		else
			throw new IOException();
	}

	@Override
	public long createAlbumAsPost(List<MultipartFile> files, AlbumDTO albumDTO, String username) throws IOException {
		Set<String> fileNames = new HashSet<String>();
		for(MultipartFile file : files) {
			String fileName = saveFile(file, storageDirectoryPath);
			String fileDownloadUri = "media/content/" + fileName;
			System.out.println(fileDownloadUri);
			fileNames.add(fileDownloadUri);
		}

		Media media = new Media();
		
		UsernameDTO usernameDTO = new UsernameDTO();
		usernameDTO.setUsername(username);
		
		ResponseEntity<String> response =  userService.getAccountName(usernameDTO);
		String accountName = response.getBody();
		
		media.setUsername(accountName);
		media.setPath(fileNames);
		media.setCreated(LocalDateTime.now());
		media = mediaRepository.save(media);
		
		Post post = new Post();
		post.setDescription(albumDTO.getDescription());
		post.setLocation(albumDTO.getLocation());
		Set<String> tags = new HashSet<>(albumDTO.getTags());
		post.setTags(tags);
		post.setMedia(media);
		postRepository.save(post);

		/*List<String> followers = profileService.getFollowers(media.getUsername());
		for(String follower : followers) {
			sendNotification(NotificationType.POST, follower, media.getUsername(), Long.toString(post.getId()));
		}*/

		return media.getId();
	}

	@Override
	public long createAlbumAsStory(List<MultipartFile> files, AlbumDTO albumDTO, String username) throws  IOException {
		Set<String> fileNames = new HashSet<String>();
		for(MultipartFile file : files) {
			String fileName = saveFile(file, storageDirectoryPath);
			String fileDownloadUri = "media/content/" + fileName;
			System.out.println(fileDownloadUri);
			fileNames.add(fileDownloadUri);
		}

		Media media = new Media();
		media.setUsername(username);
		media.setPath(fileNames);
		media.setCreated(LocalDateTime.now());
		media = mediaRepository.save(media);
		
		Story story = new Story();
		story.setDateCreated(LocalDateTime.now());
		story.setCloseFriends(albumDTO.isCloseFriends());
		story.setHighlighted(false);
		story.setMedia(media);
		storyRepository.save(story);

//		List<String> followers;
//		if(story.isCloseFriends())
//			followers = userService.getCloseFriends(media.getUsername());
//		else
//			followers = userService.getFollowers(media.getUsername());
//		for(String follower : followers) {
//			System.out.println(follower);
//			//sendNotification(NotificationType.STORY, follower, media.getUsername(), media.getUsername());
//		}

		return media.getId();
	}

	
	
	@Override
	public UrlResource getContent(String contentName) throws MalformedURLException {
		return new UrlResource("file:" + storageDirectoryPath + File.separator + contentName);
	}

	
	
	
	@Override
	public void reactOnPost(RatingDTO dto, String username) throws PostDoesNotExistException {
		Post post = postRepository.findOneById(dto.getId());
		if(post == null) {
			throw new PostDoesNotExistException("You are trying to get post that does not exist!");
		}
		Set<Rating> ratings = post.getRatings();
		RatingType type = dto.isLike() ? RatingType.LIKE : RatingType.DISLIKE;
		for(Rating rat : ratings) {
			if(rat.getUsername().equals(username) && rat.getRatingType() == type) { 
				return;
			}
		}
		Rating rating = new Rating();
		rating.setUsername(username); 
		if(dto.isLike() == false) {
			rating.setRatingType(RatingType.DISLIKE);
		}else {
			rating.setRatingType(RatingType.LIKE);
		}
		ratingRepository.save(rating);
		post.getRatings().add(rating);
		postRepository.save(post);

		//sendNotification(NotificationType.RATING, post.getMedia().getUsername(), rating.getUsername(), Long.toString(post.getId()));
	}

	@Override
	public AllReactionsDTO getAllReactions(long postId) throws PostDoesNotExistException {
		Post post = postRepository.findOneById(postId);
		if(post == null) {
			throw new PostDoesNotExistException("You are trying to get post that does not exist!");
		}
		Set<String> likes = new HashSet<String>();
		Set<String> dislikes = new HashSet<String>();
		Set<Rating> ratings = post.getRatings();
		for(Rating rat : ratings) {
			if(rat.getRatingType() == RatingType.LIKE) {
				likes.add(rat.getUsername());
			}else {
				dislikes.add(rat.getUsername());
			}
		}
		AllReactionsDTO dto = new AllReactionsDTO(likes, dislikes);
		return dto;
	}

	@Override
	public void checkProfile(long postId, String myUsername) throws PostDoesNotExistException, ProfilePrivateException, ProfileBlockedException {
		Post post = postRepository.findOneById(postId);
		if(post == null) {
			throw new PostDoesNotExistException("You are trying to get post that does not exist!");
		}
		String username = post.getMedia().getUsername();
		
		System.out.println(username);
		 List<String> followers = userService.getFollowers(username);
	        boolean follower = followers.contains(myUsername);
	        if(!userService.isPublic(username) && !follower && !username.equals(myUsername))
	            throw new ProfilePrivateException();
	     
	     List<String> blockedProfiles = userService.getBlocked(myUsername);
	      	boolean blocked = blockedProfiles.contains(username);
	        if(blocked)
	            throw new ProfileBlockedException();
	
	        
	}

	@Override
	public ReactionsNumberDTO getReactionsNumber(long id) throws PostDoesNotExistException {
		Post post = postRepository.findOneById(id);
		if(post == null) {
			throw new PostDoesNotExistException("You are trying to get post that does not exist!");
		}
		ReactionsNumberDTO dto = new ReactionsNumberDTO();
		Set<Rating> likes = new HashSet<Rating>();
		Set<Rating> dislikes = new HashSet<Rating>();
		Set<Rating> ratings = post.getRatings();
		for(Rating rating : ratings) {
			if(rating.getRatingType() == RatingType.LIKE) {
				likes.add(rating);
			}else {
				dislikes.add(rating);
			}
		}
		dto.setLikes(likes.size());
		dto.setDislikes(dislikes.size());
		return dto;
	}

	@Override
	public void postComment(CommentDTO dto, String username) throws PostDoesNotExistException {
		Comment comment = new Comment();
		comment.setUsername(username); 
		comment.setDateCreated(LocalDateTime.now());
		comment.setContent(dto.getContent());
		commentRepository.save(comment);
		
		Optional<Post> post = postRepository.findById(dto.getId());
		if(!post.isPresent())
			throw new PostDoesNotExistException("You are trying to get post that does not exist!");
		Post oldPost= post.get();
		oldPost.getComments().add(comment);
		postRepository.save(oldPost);

		//sendNotification(NotificationType.COMMENT, oldPost.getMedia().getUsername(), comment.getUsername(), Long.toString(oldPost.getId()));
	}

	@Override
	public List<AllCommentDTO> getAllComments(long postId) throws PostDoesNotExistException {
		Post post = postRepository.findOneById(postId);
		if(post == null) {
			throw new PostDoesNotExistException("You are trying to get post that does not exist!");
		}
		Set<Comment> comments =  post.getComments();
		List<Comment> comArray = new ArrayList<>(comments);
		sortByDateCreated(comArray);
		List<AllCommentDTO> contents = new ArrayList<AllCommentDTO>();
		for(Comment com : comArray) {
			contents.add(new AllCommentDTO(com.getUsername(), com.getContent()));
		}
		return contents;
	}

	private List<Comment> sortByDateCreated(List<Comment> dtos){
		Collections.sort(dtos, new Comparator<Comment>() {
		    @Override
		    public int compare(Comment c1, Comment c2) {
		        return c1.getDateCreated().compareTo(c2.getDateCreated());
		    }
		});
		return dtos;
	}
}
