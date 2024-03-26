package com.example.discreetleprechaun.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.InputStreamReader

@RestController
@RequestMapping("/api/uploadFile/")
class BankAccountSettlementRestController {

    /* Accepts a csv file as a param "file"
       Reads each line and adds "Processed" to the end of each line
       Returns a csv file as the response
     */
    @PostMapping
    fun uploadFile(@RequestParam("file") file: MultipartFile): ResponseEntity<ByteArray> {
        val csvLines = BufferedReader(InputStreamReader(file.inputStream)).use { it.readLines() }
        val result = processCSVData(csvLines) // Process the CSV data
        return if(result.isNotEmpty()){
            ResponseEntity.ok()
                .header("Content-Type", "text/csv")
                .header("Content-Disposition", "attachment; filename=processed.csv")
                .body(result.toByteArray())
        }else{
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }
    private fun processCSVData(lines: List<String>): String {
        /* probably  add some validation & Auditing */
        var result = ""
        lines.forEach {
            println(it)
            result += "$it, Processed\n"
        }
        return result
    }

}