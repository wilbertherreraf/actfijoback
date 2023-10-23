package gob.gamo.activosf.app.search;

import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class, property = "idSearch")
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonRootName(value = "search")
public class SearchCriteria {
    private Integer id;
    private String key;
    private String operation;
    private Object value;
    private Integer idPadre;
    private SearchCriteria padre;

    @Builder.Default
    private Set<SearchCriteria> children = new HashSet<>();
    /*     public SearchCriteria(final String key, final String operation, final Object value) {
           super();
           this.key = key;
           this.operation = operation;
           this.value = value;
       }
    */
    public String getKey() {
        return key;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setKey(final String key) {
        this.key = key;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(final String operation) {
        this.operation = operation;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(final Object value) {
        this.value = value;
    }

    public Integer getIdPadre() {
        return idPadre;
    }

    public void setIdPadre(Integer idPadre) {
        this.idPadre = idPadre;
    }
}
