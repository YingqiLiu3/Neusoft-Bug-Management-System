package com.dlut.bugtestmanage.controller;


import com.dlut.bugtestmanage.ResponseResult;
import com.dlut.bugtestmanage.entity.Bug;
import com.dlut.bugtestmanage.service.BugService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bugs")
public class BugController {

    @Autowired
    private BugService bugService;

    @PostMapping("/create")
    public ResponseResult createBug(@RequestBody Bug bug) {
        return bugService.createBug(bug);
    }

    @DeleteMapping("/{bugId}")
    public ResponseResult deleteBug(@PathVariable Integer bugId) {
        return bugService.deleteBug(bugId);
    }
    @PutMapping("/{bugId}")
    public ResponseResult updateBug(@PathVariable Integer bugId, @RequestBody Bug bug) {
        return bugService.updateBug(bug);
    }
    @GetMapping("/{bugId}")
    public ResponseResult getBugDetail(@PathVariable Integer bugId) {
        return bugService.getBugDetail(bugId);
    }

    @PutMapping("/{bugId}/resolve")
    public ResponseResult resolveBug(@PathVariable Integer bugId) {
        return bugService.solveBug(bugId);
    }

    @GetMapping("/querry/{projectId}")
    public ResponseResult page(
            @PathVariable("projectId") Integer projectId,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "1") Integer size
    ) {
        return bugService.page(current, size, projectId);
    }//

}
