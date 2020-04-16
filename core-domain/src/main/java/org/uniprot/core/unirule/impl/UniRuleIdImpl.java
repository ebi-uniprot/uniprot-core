package org.uniprot.core.unirule.impl;

import java.util.regex.Pattern;

import org.uniprot.core.impl.ValueImpl;
import org.uniprot.core.unirule.UniRuleId;

public class UniRuleIdImpl extends ValueImpl implements UniRuleId {

    private static final String UNIRULE_ID_REGEX = "^UR\\d{9}";
    private static final Pattern UNIRULE_ID_REGEX_PATTERN =
            Pattern.compile(UNIRULE_ID_REGEX, Pattern.CASE_INSENSITIVE);
    private static final long serialVersionUID = 5722538162124643748L;

    UniRuleIdImpl() {
        super(null);
    }

    UniRuleIdImpl(String value) {
        super(value);
    }

    @Override
    public boolean isValidId() {
        return UNIRULE_ID_REGEX_PATTERN.matcher(this.getValue()).matches();
    }
}
