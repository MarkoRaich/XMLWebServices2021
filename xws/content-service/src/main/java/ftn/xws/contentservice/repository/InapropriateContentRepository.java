package ftn.xws.contentservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ftn.xws.contentservice.model.InapropriateContent;

@Repository
public interface InapropriateContentRepository extends JpaRepository<InapropriateContent, Long> {

}