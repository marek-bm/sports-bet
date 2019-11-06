package pl.bets365mj.utils;

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@ConfigurationPropertiesBinding
public class DateConverter implements Converter<String, Date> {

    @Override
    public Date convert(String dateString) {
        try {
            DateFormat format = new SimpleDateFormat("yy-MM-dd");
            Date date = null;
            java.util.Date dateConverted = format.parse(dateString);
            return date = dateConverted;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
