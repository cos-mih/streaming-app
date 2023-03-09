package entities;

/**
 * Concrete class representing a song in the app.
 */
public class Song extends Stream{

    private Genre streamGenre;

    public Song() {

    }

    /**
     * Inner enum class defining the possible genres of a song.
     */
    public enum Genre {
        POP("pop"),
        LATIN("latin"),
        HOUSE("house"),
        DANCE("dance"),
        TRAP("trap");

        private String string;

        Genre(String string) {
            this.string = string;
        }

        public String getString() {
            return this.string;
        }
    }

    /**
     * Concrete Builder class implementing the generic functionalities of the abstract StreamBuilder.
     */
    public static class SongBuilder extends Stream.StreamBuilder<SongBuilder> {

        public SongBuilder() {
            this.stream = new Song();
        }

        @Override
        public SongBuilder getThis() {
            return this;
        }

        public SongBuilder withGenre(int genre) {
            switch (genre) {
                case 1:
                    ((Song) this.stream).streamGenre = Genre.POP;
                    break;
                case 2:
                    ((Song) this.stream).streamGenre = Genre.LATIN;
                    break;
                case 3:
                    ((Song) this.stream).streamGenre = Genre.HOUSE;
                    break;
                case 4:
                    ((Song) this.stream).streamGenre = Genre.DANCE;
                    break;
                case 5:
                    ((Song) this.stream).streamGenre = Genre.TRAP;
                    break;
            }
            return this;
        }
    }
}
