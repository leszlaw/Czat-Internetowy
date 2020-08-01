package pl.ostek.internet_chat.converter;

import org.junit.jupiter.params.converter.ArgumentConversionException;
import org.junit.jupiter.params.converter.DefaultArgumentConverter;
import org.junit.jupiter.params.converter.SimpleArgumentConverter;

public class NullConverter extends SimpleArgumentConverter {
    @Override
    protected Object convert(Object source, Class<?> targetType) throws ArgumentConversionException {
        if ("null".equals(source)) {
            return null;
        }
        return DefaultArgumentConverter.INSTANCE.convert(source, targetType);
    }
}
