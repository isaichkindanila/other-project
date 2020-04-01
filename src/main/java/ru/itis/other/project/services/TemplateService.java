package ru.itis.other.project.services;

import java.util.Map;

public interface TemplateService {

    String process(String templateName, Map<String, ?> modelMap);
}
