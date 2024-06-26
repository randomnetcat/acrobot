package com.redhat.acrobot.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.NamedQueries;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.NaturalId;

import java.util.HashSet;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;

@Entity
@NamedQueries(
        @NamedQuery(name = "findAcronymByName", query = "SELECT a FROM Acronym a WHERE lower(a.acronym) = ?1")
)
public class Acronym implements java.io.Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @NaturalId
    private String acronym;

    @OneToMany(mappedBy = Explanation_.ACRONYM, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Explanation> explanations = new HashSet<>();

    public Acronym() {
    }

    public Acronym(String acronym) {
        this.acronym = acronym;
    }

    public int getId() {
        return id;
    }

    protected void setId(int id) {
        this.id = id;
    }

    public String getAcronym() {
        return acronym;
    }

    protected void setAcronym(String acronym) {
        this.acronym = acronym;
    }

    public Set<Explanation> getExplanations() {
        return explanations;
    }

    protected void setExplanations(Set<Explanation> explanations) {
        this.explanations = explanations;
    }

    public Explanation createExplanation(String authorId, String explanation) {
        var created = new Explanation(this, authorId, explanation);

        if (explanations == null) {
            explanations = new HashSet<>();
        }

        explanations.add(created);

        return created;
    }

    @Override
    public String toString() {
        return "Acronym{" +
                "id=" + id +
                ", acronym='" + acronym + '\'' +
                ", explanations=" + explanations +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Acronym that)) return false;
        return Objects.equals(acronym, that.acronym);
    }

    @Override
    public int hashCode() {
        return Objects.hash(acronym);
    }

    public static String normalizeText(String acronym) {
        return acronym.toUpperCase(Locale.ROOT);
    }
}
