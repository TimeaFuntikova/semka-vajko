package com.semestral.repository.impl;

import org.springframework.data.jpa.repository.JpaRepository;
import com.semestral.entity.Module;

public interface ModuleRepository extends JpaRepository<Module, Long> {}