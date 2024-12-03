package com.dlut.bugtestmanage.controller;


import com.dlut.bugtestmanage.ResponseResult;
import com.dlut.bugtestmanage.entity.ProjectModule;
import com.dlut.bugtestmanage.entity.TestCase;
import com.dlut.bugtestmanage.service.TestCaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/testcases")
public class TestCaseController {
    @Autowired
    private TestCaseService testCaseService;

    @PostMapping("/create")
    public ResponseResult createTestCase(@RequestBody TestCase testCase) {
        return testCaseService.createTestCase(testCase);
    }

    @PutMapping("/{testCaseId}")
    public ResponseResult update(@PathVariable Integer testCaseId,@RequestBody TestCase testCase){

        return testCaseService.update(testCase);
    }

    @GetMapping("/{projectId}")
    public ResponseResult page(
            @PathVariable("projectId") Integer projectId,
            @RequestParam(defaultValue = "1") Integer current,
            @RequestParam(defaultValue = "1") Integer size
    ) {
        return testCaseService.page(current, size, projectId);
    }//

    @DeleteMapping("/{testCaseId}")
    public ResponseResult delete(@PathVariable Integer testCaseId){
        return testCaseService.delete(testCaseId);
    }


}
