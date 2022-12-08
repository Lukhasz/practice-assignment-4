package se.jensen.javacourse.week4.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
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

        boolean hasNamesOnly = "true".equals(req.getParameter("namesOnly"));
            if (hasNamesOnly) {
                return libraryService.getArtistsNamesOnly();
            }
            return libraryService.getArtists();
    }

    @PostMapping("/artists")
    public void createArtist(@RequestBody Artist body) {
        Artist artist = new Artist(body.getName());
        libraryService.createArtist(artist);
    }

    @PutMapping("/artists/{id}")
    public void updateArtist(@PathVariable("id") int id, @RequestBody Artist body) {
        libraryService.updateArtist(id, body);
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
    public void addTrack(@PathVariable("artistId") int id, @RequestBody Track body) {
        libraryService.addTrack(id, body);
    }

    @PutMapping("/artists/{artistId}/tracks/{trackId}")
    public void updateTrack(@PathVariable("artistId") int aid, @PathVariable("trackId") int tid, @RequestBody Track tbody) {
        libraryService.updateTrack(aid, tid, tbody);
    }

    @DeleteMapping("/artists/{artistId}/tracks/{trackId}")
    public void deleteTrack(@PathVariable("artistId") int aid, @PathVariable("trackId") int tid) {
        libraryService.deleteTrack(aid, tid);
    }

}




















