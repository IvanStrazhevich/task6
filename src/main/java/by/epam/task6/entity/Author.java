package by.epam.task6.entity;

public class Author extends Entity{

    private int authorId;
    private String authorName;
    private String authorLastName;

    public Author() {
    }


    public Author(int id, String authorName, String authorLastName) {
        super(id);
        this.authorId = id;
        this.authorName = authorName;
        this.authorLastName = authorLastName;
    }

    public int getAuthorId() {
        return authorId;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getAuthorLastName() {
        return authorLastName;
    }

    public void setAuthorLastName(String authorLastName) {
        this.authorLastName = authorLastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        Author author = (Author) o;

        if (authorId != author.authorId) return false;
        if (authorName != null ? !authorName.equals(author.authorName) : author.authorName != null) return false;
        return authorLastName != null ? authorLastName.equals(author.authorLastName) : author.authorLastName == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + authorId;
        result = 31 * result + (authorName != null ? authorName.hashCode() : 0);
        result = 31 * result + (authorLastName != null ? authorLastName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Author{" +
                "authorId=" + authorId +
                ", authorName='" + authorName + '\'' +
                ", authorLastName='" + authorLastName + '\'' +
                '}';
    }
}
