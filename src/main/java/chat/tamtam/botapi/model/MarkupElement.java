/*
 * ------------------------------------------------------------------------
 * TamTam chat Bot API
 * ------------------------------------------------------------------------
 * Copyright (C) 2018 Mail.Ru Group
 * ------------------------------------------------------------------------
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * ------------------------------------------------------------------------
 */

package chat.tamtam.botapi.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;


/**
 * MarkupElement
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "type", visible = true, defaultImpl = MarkupElement.class, include = JsonTypeInfo.As.EXISTING_PROPERTY)
@JsonSubTypes({
  @JsonSubTypes.Type(value = StrongMarkup.class, name = MarkupElement.STRONG),
  @JsonSubTypes.Type(value = EmphasizedMarkup.class, name = MarkupElement.EMPHASIZED),
  @JsonSubTypes.Type(value = MonospacedMarkup.class, name = MarkupElement.MONOSPACED),
  @JsonSubTypes.Type(value = LinkMarkup.class, name = MarkupElement.LINK),
  @JsonSubTypes.Type(value = StrikethroughMarkup.class, name = MarkupElement.STRIKETHROUGH),
  @JsonSubTypes.Type(value = UnderlineMarkup.class, name = MarkupElement.UNDERLINE),
  @JsonSubTypes.Type(value = UserMentionMarkup.class, name = MarkupElement.USER_MENTION),
  @JsonSubTypes.Type(value = HeadingMarkup.class, name = MarkupElement.HEADING),
  @JsonSubTypes.Type(value = CodeMarkup.class, name = MarkupElement.CODE),
})
@KnownInstance(ofClass = MarkupElement.class, discriminator = "type")
public class MarkupElement implements TamTamSerializable {
    public static final String STRONG = "strong";
    public static final String EMPHASIZED = "emphasized";
    public static final String MONOSPACED = "monospaced";
    public static final String LINK = "link";
    public static final String STRIKETHROUGH = "strikethrough";
    public static final String UNDERLINE = "underline";
    public static final String USER_MENTION = "user_mention";
    public static final String HEADING = "heading";
    public static final String CODE = "code";
    public static final Set<String> TYPES = new HashSet<>(Arrays.asList(
        STRONG, 
        EMPHASIZED, 
        MONOSPACED, 
        LINK, 
        STRIKETHROUGH, 
        UNDERLINE, 
        USER_MENTION, 
        HEADING, 
        CODE
    ));

    @NotNull
    private final @Valid Integer from;
    @NotNull
    private final @Valid Integer length;

    @JsonCreator
    public MarkupElement(@JsonProperty("from") Integer from, @JsonProperty("length") Integer length) { 
        this.from = from;
        this.length = length;
    }



    public void visit(Visitor visitor) {
        visitor.visitDefault(this);
    }

    public <T> T map(Mapper<T> mapper) {
        return mapper.mapDefault(this);
    }

    /**
    * Element start index (zero-based) in text
    * @return from
    **/
    @JsonProperty("from")
    public Integer getFrom() {
        return from;
    }

    /**
    * Length of the markup element
    * @return length
    **/
    @JsonProperty("length")
    public Integer getLength() {
        return length;
    }

    @JsonProperty("type")
    public String getType() {
        throw new UnsupportedOperationException("Model has no concrete type.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
          return true;
        }
        if (o == null || getClass() != o.getClass()) {
          return false;
        }

        MarkupElement other = (MarkupElement) o;
        return Objects.equals(this.from, other.from) &&
            Objects.equals(this.length, other.length);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + (from != null ? from.hashCode() : 0);
        result = 31 * result + (length != null ? length.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MarkupElement{"
            + " from='" + from + '\''
            + " length='" + length + '\''
            + '}';
    }

    public interface Visitor {
        void visit(StrongMarkup model);
        void visit(EmphasizedMarkup model);
        void visit(MonospacedMarkup model);
        void visit(LinkMarkup model);
        void visit(StrikethroughMarkup model);
        void visit(UnderlineMarkup model);
        void visit(UserMentionMarkup model);
        void visit(HeadingMarkup model);
        void visit(CodeMarkup model);
        void visitDefault(MarkupElement model);
    }

    public interface Mapper<T> {
        T map(StrongMarkup model);
        T map(EmphasizedMarkup model);
        T map(MonospacedMarkup model);
        T map(LinkMarkup model);
        T map(StrikethroughMarkup model);
        T map(UnderlineMarkup model);
        T map(UserMentionMarkup model);
        T map(HeadingMarkup model);
        T map(CodeMarkup model);
        T mapDefault(MarkupElement model);
    }
}
