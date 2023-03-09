package entities;

/**
 * Concrete class representing an audiobook in the system.
 */
public class Audiobook extends Stream {

    private Genre streamGenre;

    public Audiobook() {

    }

    /**
     * Inner enum class defining the possible genres of an audiobook.
     */
    private enum Genre {
        FICTION("fiction"),
        PERSONALDEV("personal development"),
        CHILDREN("children");

        private String string;

        Genre(String string) {
            this.string = string;
        }

        public String getString() {
            return string;
        }
    }


    /**
     * Concrete Builder class implementing the generic functionalities of the abstract StreamBuilder.
     */
    public static class AudiobookBuilder extends Stream.StreamBuilder<AudiobookBuilder> {

        public AudiobookBuilder() {
            this.stream = new Audiobook();
        }

        @Override
        public AudiobookBuilder getThis() {
            return this;
        }

        public AudiobookBuilder withGenre(int genre) {
            switch (genre) {
                case 1:
                    ((Audiobook) this.stream).streamGenre = Genre.FICTION;
                    break;
                case 2:
                    ((Audiobook) this.stream).streamGenre = Genre.PERSONALDEV;
                    break;
                case 3:
                    ((Audiobook) this.stream).streamGenre = Genre.CHILDREN;
                    break;
            }
            return this;
        }
    }
}
