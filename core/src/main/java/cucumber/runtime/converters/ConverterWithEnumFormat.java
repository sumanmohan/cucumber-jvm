package cucumber.runtime.converters;

import java.text.FieldPosition;
import java.text.Format;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class ConverterWithEnumFormat<T extends Enum> extends ConverterWithFormat<T> {

    private final List<Format> formats = new ArrayList<Format>();
    private Locale locale;
    private Class<? extends Enum> typeClass;

    public ConverterWithEnumFormat(Locale locale, Class<? extends Enum> enumClass) {
        super(new Class[]{enumClass});
        this.locale = locale;
        this.typeClass = enumClass;
        formats.add(new LowercaseFormat());
        formats.add(new UppercaseFormat());
        formats.add(new CapitalizeFormat());
    }


    @Override
    public T fromString(String string) {
        T s = super.fromString(string);
        return s == null ? null : s;
    }

    @Override
    public List<Format> getFormats() {
        return formats;
    }

    private class LowercaseFormat extends Format {

        @Override
        public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
            return toAppendTo.append(String.valueOf(obj));
        }

        @Override
        public Object parseObject(String source, ParsePosition pos) {
            return source == null ? null : Enum.valueOf(typeClass, source.toLowerCase(locale));
        }
    }

    private class UppercaseFormat extends Format {
        @Override
        public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
            return toAppendTo.append(String.valueOf(obj));
        }

        @Override
        public Object parseObject(String source, ParsePosition pos) {
            return source == null ? null : Enum.valueOf(typeClass, source.toUpperCase(locale));
        }
    }

    private class CapitalizeFormat extends Format {
        @Override
        public StringBuffer format(Object obj, StringBuffer toAppendTo, FieldPosition pos) {
            return toAppendTo.append(String.valueOf(obj));
        }

        @Override
        public Object parseObject(String source, ParsePosition pos) {
            String firstLetter = source.substring(0, 1);
            String restOfTheString = source.substring(1, source.length());
            return source == null ? null : Enum.valueOf(typeClass, firstLetter.toUpperCase(locale) + restOfTheString);
        }
    }

}