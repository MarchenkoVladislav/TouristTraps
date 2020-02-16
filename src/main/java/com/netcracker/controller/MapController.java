package com.netcracker.controller;

import com.netcracker.db.entity.Photo;
import com.netcracker.db.entity.User;
import com.netcracker.db.service.PhotosService;
import com.netcracker.db.service.RoutesService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Controller
@SessionAttributes("routeId")
public class MapController {

    @Value("${upload.path}")
    private String UPLOADED_FOLDER;

    @Autowired
    private PhotosService photosService;

    private JSONObject getSuccessMessage() {
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("0", "{\"success\":true}");

        return jsonObject;
    }

    @GetMapping("/")
    public String ourPage(Model model ,@AuthenticationPrincipal User user ) {
        Map<String, Object> data = new HashMap<>();

        data.put("user", user);

        model.addAttribute("signInData", data);

        return "ourPage";
    }


    @DeleteMapping(value = "/{qquuid}")
    public @ResponseBody String uploadDelete(HttpServletRequest request, @PathVariable("qquuid") String qquuid) throws IOException {

        String fileName = (String) request.getSession().getAttribute(qquuid);

            if (fileName != null) {
                Path path = Paths.get(UPLOADED_FOLDER + fileName);
                Files.delete(path);
            }

        return getSuccessMessage().toString();
    }

    @PostMapping(value = "/")
    public @ResponseBody Object upload(@RequestParam("file") MultipartFile file, HttpServletRequest request , HttpSession session)
            throws IOException {
        String photoName = "";
        String pathToPhoto = "";

        if (file.isEmpty()) {
            request.setAttribute("message",
                    "Please select a file to upload");
            return "uploadStatus";
        }

        String qquuid = request.getParameter("qquuid");
        System.out.println("qquuid=" + qquuid);
        if (qquuid != null) {
            request.getSession().setAttribute(qquuid,
                    file.getOriginalFilename());
        }

        String uuidFile = UUID.randomUUID().toString();
        String resultFilename = uuidFile + file.getOriginalFilename();
        file.transferTo(new File(UPLOADED_FOLDER + "/" + resultFilename));

        Integer routeID1 = (Integer) session.getAttribute("routeId");
        Photo photo = new Photo(routeID1, photoName, pathToPhoto);
        photo.setRouteID(routeID1);
        session.removeAttribute("routeId");
        photo.setPhotoName(resultFilename);
        photo.setPathToPhoto(UPLOADED_FOLDER);
        photosService.createOrUpdatePhoto(photo);

        return getSuccessMessage().toString();
    }

    @RequestMapping("/BootstrapFileInput")
    public String bootstrapFileInput(Map<String, Object> model) {
        return "BootstrapFileInput";
    }

    @RequestMapping("/Dropzone")
    public String dropzone(Map<String, Object> model) {
        return "Dropzone";
    }

}
