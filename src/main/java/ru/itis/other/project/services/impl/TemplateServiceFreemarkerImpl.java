package ru.itis.other.project.services.impl;

import freemarker.template.Configuration;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import ru.itis.other.project.services.TemplateService;

import java.util.Map;

@Service
@AllArgsConstructor
class TemplateServiceFreemarkerImpl implements TemplateService {

    private final Configuration configuration;

    @Override
    @SneakyThrows
    public String process(String templateName, Map<String, ?> modelMap) {
        var template = configuration.getTemplate(templateName + ".ftl");
        return FreeMarkerTemplateUtils.processTemplateIntoString(template, modelMap);
    }
}
