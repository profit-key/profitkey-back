package com.profitkey.stock.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.profitkey.stock.entity.UploadFile;

public interface UploadFileRepository extends JpaRepository<UploadFile, Long> {
}
