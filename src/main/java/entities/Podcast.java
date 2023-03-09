package entities;

/**
 * Concrete class representing a podcast in the system.
 */
public class Podcast extends Stream {

    private Genre streamGenre;

    public Podcast() {

    }

    /**
     * Inner enum class defining the possible genres of a podcast.
     */
    private enum Genre {
        DOCUMENTARY("documentary"),
        CELEBRITIES("celebrities"),
        TECH("tech");

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
    public static class PodcastBuilder extends Stream.StreamBuilder<PodcastBuilder> {
        public PodcastBuilder() {
            this.stream = new Podcast();
        }

        @Override
        public PodcastBuilder getThis() {
            return this;
        }

        public PodcastBuilder withGenre(int genre) {
            switch (genre) {
                case 1:
                    ((Podcast)this.stream).streamGenre = Genre.DOCUMENTARY;
                    break;
                case 2:
                    ((Podcast)this.stream).streamGenre = Genre.CELEBRITIES;
                    break;
                case 3:
                    ((Podcast)this.stream).streamGenre = Genre.TECH;
                    break;
            }
            return this;
        }
    }
}
