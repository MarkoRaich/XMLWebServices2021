package ftn.xws.contentservice.service;

import java.util.List;

import ftn.xws.contentservice.dto.CollectionDTO;
import ftn.xws.contentservice.dto.CollectionInfoDTO;
import ftn.xws.contentservice.dto.PostInfoDTO;
import ftn.xws.contentservice.dto.SearchResultDTO;
import ftn.xws.contentservice.exception.PostDoesNotExistException;
import ftn.xws.contentservice.exception.ProfileBlockedException;
import ftn.xws.contentservice.exception.ProfilePrivateException;
import ftn.xws.contentservice.model.Favourites;

public interface PostService {
	
	List<PostInfoDTO> getFeed(String username);

    List<PostInfoDTO> getForProfile(String requestedBy, String profile) throws ProfilePrivateException, ProfileBlockedException;

    List<SearchResultDTO> search(String requestedBy, String criterion);

    List<PostInfoDTO> getAllWithLocation(String requestedBy, String location);

    List<PostInfoDTO> getAllWithHashtag(String requestedBy, String hashtag);

    PostInfoDTO get(String requestedBy, long postId) throws PostDoesNotExistException;
    
    Favourites saveToFavourites(long postId ,String username);
    
    List<PostInfoDTO> getFavouritesForProfile(String profile);

	void addFavouritesToCollection(String loggedInUsername, CollectionDTO dto);

	List<CollectionInfoDTO> getCollectionsForProfile(String loggedInUsername);

	List<PostInfoDTO> getLikedContent(String username);

	List<PostInfoDTO> getDislikedContent(String username);

}
