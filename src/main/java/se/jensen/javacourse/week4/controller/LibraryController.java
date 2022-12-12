package se.jensen.javacourse.week4.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import se.jensen.javacourse.week4.model.Artist;
import se.jensen.javacourse.week4.model.Track;
import se.jensen.javacourse.week4.service.LibraryService;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Map;

@RestController
@RequestMapping ("/api/v1")
public class LibraryController {

    @Autowired
    LibraryService libraryService = new LibraryService();
    private static final ArrayList<Artist> name = new ArrayList<>();


    @GetMapping("/artists")
    public Object getArtists(HttpServletRequest req) {

        boolean namesOnly = "true".equals(req.getParameter("namesOnly"));
            if (namesOnly)
                return libraryService.getArtistsNamesOnly();
            return libraryService.getArtists();
    }

    @PostMapping("/artists")
    public ResponseEntity createArtist(@RequestBody Artist body) {
        int response = libraryService.createArtist(body);
        switch (response) {
            default:
            case 0:
                return new ResponseEntity(HttpStatus.CREATED);
            case -1:
                return new ResponseEntity("Artist with that name already exists", HttpStatus.FORBIDDEN);
            case -2:
                return new ResponseEntity("Cannot create artist with empty name", HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/artists/{id}")
    public ResponseEntity updateArtist(@PathVariable("id") int id, @RequestBody Artist body) {
        int response = libraryService.updateArtist(id, body);
        switch (response) {
            default:
            case 0:
                return new ResponseEntity(HttpStatus.OK);
            case -1:
                return new ResponseEntity("An artist with that name already exists", HttpStatus.FORBIDDEN);
            case -2:
                return new ResponseEntity("No artist with ID: " + id + " was found", HttpStatus.BAD_REQUEST);

        }
    }

    @DeleteMapping("/artists/{id}")
    public void deleteArtist(@PathVariable("id") int id) {
        libraryService.deleteArtist(id);
    }

    @GetMapping("/tracks")
    public void getTracks(HttpServletRequest req) {
        libraryService.getTracks();
    }

    @PostMapping("/artists/{artistId}/tracks")
    public ResponseEntity addTrack(@PathVariable("artistId") int id, @RequestBody Track body) {
        int response = libraryService.addTrack(id, body);
        switch (response) {
            default:
            case 0:
                return new ResponseEntity(HttpStatus.CREATED);
            case -1:
                return new ResponseEntity("Artist with ID " + id + " already has a track with that name", HttpStatus.FORBIDDEN);
            case -2:
                return new ResponseEntity("No artist with ID: " + id + " exists", HttpStatus.BAD_REQUEST);
            case -3:
                return new ResponseEntity("Please specify which track to add, cannot be null or empty", HttpStatus.I_AM_A_TEAPOT);
        }
    }

    @PutMapping("/artists/{artistId}/tracks/{trackId}")
    public ResponseEntity updateTrack(@PathVariable("artistId") int aid, @PathVariable("trackId") int tid, @RequestBody Track tbody) {
        int response = libraryService.updateTrack(aid, tid, tbody);
        switch (response) {
            default:
            case 0:
                return new ResponseEntity(HttpStatus.OK);
            case -1:
                return new ResponseEntity("Artist with ID " + aid + " already has a track with name "+ tbody, HttpStatus.FORBIDDEN);
            case -2:
                return new ResponseEntity("No artist with ID: " + aid + " exists", HttpStatus.BAD_REQUEST);
        }
    }

    //Couldn't get the responses to turn up in Postman, and the HTTPStatuses didn't work either
    @DeleteMapping("/artists/{artistId}/tracks/{trackId}")
    public ResponseEntity deleteTrack(@PathVariable("artistId") int aid, @PathVariable("trackId") int tid) {
        int response = libraryService.deleteTrack(aid, tid);
        switch (response) {
            default:
            case 0:
                return new ResponseEntity(HttpStatus.OK);
            case -1:
                return new ResponseEntity("No track with ID " + tid + " exists", HttpStatus.FORBIDDEN);
            case -2:
                return new ResponseEntity("No artist with ID: " + aid + " exists", HttpStatus.BAD_REQUEST);
        }
    }

}




















