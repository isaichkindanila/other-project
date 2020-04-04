package ru.itis.other.project.services;

import ru.itis.other.project.util.annotations.DoNotLog;

import java.util.Map;

public interface TemplateService {

    @DoNotLog String process(String templateName, Map<String, ?> modelMap);
}
