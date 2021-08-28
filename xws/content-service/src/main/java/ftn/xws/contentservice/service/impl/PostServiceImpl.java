package ftn.xws.contentservice.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ftn.xws.contentservice.dto.CollectionDTO;
import ftn.xws.contentservice.dto.CollectionInfoDTO;
import ftn.xws.contentservice.dto.PostInfoDTO;
import ftn.xws.contentservice.dto.SearchResultDTO;
import ftn.xws.contentservice.dto.UsernameDTO;
import ftn.xws.contentservice.enumeration.RatingType;
import ftn.xws.contentservice.exception.PostDoesNotExistException;
import ftn.xws.contentservice.exception.ProfileBlockedException;
import ftn.xws.contentservice.exception.ProfilePrivateException;
import ftn.xws.contentservice.model.Collection;
import ftn.xws.contentservice.model.Favourites;
import ftn.xws.contentservice.model.Post;
import ftn.xws.contentservice.model.Rating;
import ftn.xws.contentservice.repository.CollectionRepository;
import ftn.xws.contentservice.repository.FavouritesRepository;
import ftn.xws.contentservice.repository.PostRepository;
import ftn.xws.contentservice.service.PostService;
import ftn.xws.contentservice.service.UserService;

@Service
public class PostServiceImpl implements PostService {
	
	
	@Autowired
	private PostRepository postRepository;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private FavouritesRepository favouritesRepository;
	
	@Autowired
	private CollectionRepository collectionRepository;


	
	@Override
	public List<PostInfoDTO> getFeed(String username) {
		System.out.println("USERNAME FROM TOKEN: " + username);
		List<PostInfoDTO> result = new ArrayList<>();

		List<String> targetedProfiles = userService.getFollowing(username);
		List<String> mutedProfiles = userService.getMuted(username);
		List<String> blockedProfiles = userService.getBlocked(username);
		List<String> inactiveProfiles = userService.getAllInactiveProfiles();
		
		targetedProfiles.removeAll(mutedProfiles);
		targetedProfiles.removeAll(blockedProfiles);
		targetedProfiles.removeAll(inactiveProfiles);

		List<Post> targetedPosts = postRepository.findAll().stream()
				.filter(p -> targetedProfiles.contains(p.getMedia().getUsername())).collect(Collectors.toList());
		Collectors.toList();

		for (Post post : targetedPosts)
			result.add(toPostInfoDTO(post));
		postRepository.findAll().stream().filter(p -> p.getMedia().getUsername().equals(username))
				.forEach(p -> result.add(toPostInfoDTO(p)));

		result.sort((r1, r2) -> r1.getCreated().isBefore(r2.getCreated()) ? 1 : -1);
		return result;
	}

	@Override
	public List<PostInfoDTO> getForProfile(String requestedBy, String profile) throws ProfilePrivateException, ProfileBlockedException {
		
		System.out.println("USERNAME FROM TOKEN: " + requestedBy);
		
		List<PostInfoDTO> result;
		
		if (requestedBy == null)
			result = getForProfileWhenUnauthenticated(profile);
		else
			result = getForProfileWhenAuthenticated(requestedBy, profile);
			result.sort((r1, r2) -> r1.getCreated().isBefore(r2.getCreated()) ? 1 : -1);
		return result;
	}

	@Override
	public List<SearchResultDTO> search(String requestedBy, String criterion) {
		
		List<SearchResultDTO> result = new ArrayList<>();
		
		for (String location : getAllLocations(requestedBy))
			if (location.contains(criterion.toLowerCase()))
				result.add(new SearchResultDTO(location, "location"));
		
		for (String hashtag : getAllHashtags(requestedBy))
			if (hashtag.contains(criterion.toLowerCase()))
				result.add(new SearchResultDTO(hashtag, "hashtag"));
		
		for (String profile : getAllProfiles(requestedBy)) {

			if (profile.toLowerCase().contains(criterion.toLowerCase()))
				result.add(new SearchResultDTO(profile, "profile"));
		  	}
		
		return result;
	}

	@Override
	public List<PostInfoDTO> getAllWithLocation(String requestedBy, String location) {
		List<Post> posts = postRepository.findAll().stream()
				.filter(p -> (userService.isPublic(p.getMedia().getUsername())
						|| p.getMedia().getUsername().equals(requestedBy)
						|| (userService.getFollowing(p.getMedia().getUsername()).contains(requestedBy)
								&& !userService.getBlocked(requestedBy).contains(p.getMedia().getUsername())))
						&& p.getLocation() != null && location.toLowerCase().equals(p.getLocation().toLowerCase()))
				.collect(Collectors.toList());
		return posts.stream().map(p -> toPostInfoDTO(p)).collect(Collectors.toList());
	}

	@Override
	public List<PostInfoDTO> getAllWithHashtag(String requestedBy, String hashtag) {
		List<Post> posts = postRepository.findAll().stream()
				.filter(p -> (userService.isPublic(p.getMedia().getUsername())
						|| p.getMedia().getUsername().equals(requestedBy)
						|| (userService.getFollowing(p.getMedia().getUsername()).contains(requestedBy)
								&& !userService.getBlocked(requestedBy).contains(p.getMedia().getUsername())))
						&& p.getTags() != null
						&& p.getTags().stream().filter(t -> hashtag.toLowerCase().equals(t.toLowerCase())).count() > 0)
				.collect(Collectors.toList());
		return posts.stream().map(p -> toPostInfoDTO(p)).collect(Collectors.toList());
	}

	@Override
	public PostInfoDTO get(String requestedBy, long postId) throws PostDoesNotExistException {
		System.out.println("USERNAME FROM TOKEN: " + requestedBy);
		Post post = postRepository.findAll().stream()
				.filter(p -> (userService.isPublic(p.getMedia().getUsername())
						|| p.getMedia().getUsername().equals(requestedBy)
						|| (userService.getFollowers(p.getMedia().getUsername()).contains(requestedBy)
								&& !userService.getBlocked(requestedBy).contains(p.getMedia().getUsername())))
						&& p.getId() == postId)
				.findFirst().orElse(null);
		if (post == null)
			throw new PostDoesNotExistException();
		return toPostInfoDTO(post);
	}

	@Override
	public Favourites saveToFavourites(long postId, String username) {
		Favourites favourite = new Favourites();
		Post post = postRepository.findById(postId).get();
		favourite.setPost(post);
		favourite.setUsername(username);

		boolean exists = false;
		for (Favourites f : favouritesRepository.findAll()) {
			if (f.getPost().equals(post) && f.getUsername().equals(username)) {
				exists = true;
			}
		}
		if (!exists) {
			favouritesRepository.save(favourite);
		}
		return favourite;
	}

	@Override
	public List<PostInfoDTO> getFavouritesForProfile(String profile) {
		List<PostInfoDTO> result = new ArrayList<PostInfoDTO>();
		for (Favourites f : favouritesRepository.findAll().stream().filter(p -> p.getUsername().equals(profile))
				.collect(Collectors.toList())) {
			for (Post p : postRepository.findAll()) {
				if (p.getId() == f.getPost().getId()) {
					result.add(toPostInfoDTO(p));
				}
			}
		}
		return result;
	}

	@Override
	public void addFavouritesToCollection(String loggedInUsername, CollectionDTO dto) {
		Collection collection = new Collection();
		collection.setUsername(loggedInUsername);
		Post post = postRepository.findOneById(dto.getId());
		Favourites fav = favouritesRepository.findByPost(post);
		collection.setFavourite(fav);
		collection.setName(dto.getName());

		collectionRepository.save(collection);
	}

	@Override
	public List<CollectionInfoDTO> getCollectionsForProfile(String loggedInUsername) {
		List<CollectionInfoDTO> result = new ArrayList<CollectionInfoDTO>();
		for (Collection c : collectionRepository.findAll().stream()
				.filter(c -> c.getUsername().equals(loggedInUsername)).collect(Collectors.toList())) {
			result.add(toCollectionInfoDto(c));
		}
		return result;
	}
	
	private CollectionInfoDTO toCollectionInfoDto(Collection c) {
		CollectionInfoDTO dto = new CollectionInfoDTO();
		dto.setId(c.getId());
		dto.setName(c.getName());
		dto.setUrls(c.getFavourite().getPost().getMedia().getPath());
		return dto;
	}

	@Override
	public List<PostInfoDTO> getLikedContent(String username) {
		List<PostInfoDTO> result = new ArrayList<>();
		

		List<String> targetedProfiles = userService.getAll();
		List<String> blockedProfiles = userService.getBlocked(username);
		targetedProfiles.removeAll(blockedProfiles);
		
		List<Post> targetedPostsWithoutBlockedProfiles = postRepository.findAll().stream()
				.filter(p -> targetedProfiles.contains(p.getMedia().getUsername())).collect(Collectors.toList());
		Collectors.toList();
		
		for(Post post : targetedPostsWithoutBlockedProfiles) {
			for(Rating rating : post.getRatings())
			{
				if(rating.getUsername().equals(username) && rating.getRatingType() == RatingType.LIKE) {
					result.add(toPostInfoDTO(post));
				}
			}
		}
		result.sort((r1, r2) -> r1.getCreated().isBefore(r2.getCreated()) ? 1 : -1);
		return result;
	}

	@Override
	public List<PostInfoDTO> getDislikedContent(String username) {
		List<PostInfoDTO> result = new ArrayList<>();
		

		List<String> targetedProfiles = userService.getAll();
		List<String> blockedProfiles = userService.getBlocked(username);
		targetedProfiles.removeAll(blockedProfiles);
		
		List<Post> targetedPostsWithoutBlockedProfiles = postRepository.findAll().stream()
				.filter(p -> targetedProfiles.contains(p.getMedia().getUsername())).collect(Collectors.toList());
		Collectors.toList();
		
		for(Post post : targetedPostsWithoutBlockedProfiles) {
			for(Rating rating : post.getRatings())
			{
				if(rating.getUsername().equals(username) && rating.getRatingType() == RatingType.DISLIKE) {
					result.add(toPostInfoDTO(post));
				}
			}
		}
		result.sort((r1, r2) -> r1.getCreated().isBefore(r2.getCreated()) ? 1 : -1);
		return result;
	}
	
	private PostInfoDTO toPostInfoDTO(Post post) {
		PostInfoDTO result = new PostInfoDTO();
		result.setId(post.getId());
		result.setUsername(post.getMedia().getUsername());
		result.setDescription(post.getDescription());
		result.setHashtags(post.getTags());
		result.setLocation(post.getLocation());
		result.setCreated(post.getMedia().getCreated());
		result.setUrls(post.getMedia().getPath());
		return result;
	}
	
	private List<PostInfoDTO> getForProfileWhenUnauthenticated(String profile) throws ProfilePrivateException {
		
		if (!userService.isPublic(profile))
			throw new ProfilePrivateException();
		
		return getForProfile(profile);
	}
	
	private List<PostInfoDTO> getForProfile(String profile) {
		
		//String username = userService.getUsernameForAccountName(profile);
		
		List<Post> posts = postRepository.findAll().stream().filter(p -> p.getMedia().getUsername().equals(profile))
							.collect(Collectors.toList());
		
		return posts.stream().map(p -> toPostInfoDTO(p)).collect(Collectors.toList());
		
	}
	
	private List<PostInfoDTO> getForProfileWhenAuthenticated(String requestedBy, String profile) throws ProfilePrivateException, ProfileBlockedException {
		
		List<String> followers = userService.getFollowers(profile);
		
		
		UsernameDTO usernameDTO = new UsernameDTO();
		usernameDTO.setUsername(requestedBy);
		
		ResponseEntity<String> response =  userService.getAccountName(usernameDTO);
		
		boolean follower = followers.contains(response.getBody());
		
		
		if (!userService.isPublic(profile) && !follower && !profile.equals(response.getBody()))
			throw new ProfilePrivateException();

		List<String> blockedProfiles = userService.getBlocked(requestedBy);
		boolean blocked = blockedProfiles.contains(profile);
		if (blocked)
			throw new ProfileBlockedException();

		return getForProfile(profile);
	}
	
	private Set<String> getAllLocations(String requestedBy) {
		
		Set<String> result = new HashSet<>();
		
		postRepository.findAll().stream()
				.filter(p -> p.getLocation() != null && ( userService.isPublic(p.getMedia().getUsername()) ||
														  p.getMedia().getUsername().equals(requestedBy) || 
				( userService.getFollowing(p.getMedia().getUsername()).contains(requestedBy) && !userService.getBlocked(requestedBy).contains(p.getMedia().getUsername()))
														)
						)
				.forEach(p -> result.add(p.getLocation().toLowerCase()));
		return result;
	}

	private Set<String> getAllHashtags(String requestedBy) {
		Set<String> result = new HashSet<>();
		postRepository.findAll().stream()
				.filter(p -> p.getTags() != null && (userService.isPublic(p.getMedia().getUsername())
						|| p.getMedia().getUsername().equals(requestedBy)
						|| (userService.getFollowing(p.getMedia().getUsername()).contains(requestedBy)
								&& !userService.getBlocked(requestedBy).contains(p.getMedia().getUsername()))))
				.forEach(p -> p.getTags().forEach(t -> result.add(t.toLowerCase())));
		return result;
	}

	private Set<String> getAllProfiles(String requestedBy) {
		
		if (requestedBy != null) {
			return userService.getAll().stream().filter(p -> !userService.getBlocked(requestedBy).contains(p) && !userService.getAllInactiveProfiles().contains(p))
					.collect(Collectors.toSet());
		}
		return userService.getAll().stream().collect(Collectors.toSet());
	}

}
