package restaurant.app.langMessage;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class Language {
    private LanguageEnum lang;
    public Language(String lang) {
        this.lang = LanguageEnum.valueOf(lang);
    }
    public LanguageEnum getEnumValue() {
        return lang;
    }
}
