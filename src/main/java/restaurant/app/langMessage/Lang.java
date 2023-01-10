package restaurant.app.langMessage;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class Lang {
    private Language lang;
    public Lang(String lang) {
        this.lang = Language.valueOf(lang);
    }
    public Language getEnumValue() {
        return lang;
    }
}
