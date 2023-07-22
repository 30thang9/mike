package com.nth.mike.service;

import com.nth.mike.entity.Color;
import com.nth.mike.entity.Size;

import java.util.List;

public interface ColorService {
    List<Color> findAll();
    Color findById(Long id);
    Color save(Color color);
    Long deleteById(Long id);
}
