package controllers;

import com.capsulecrm.rest.COrganisation;
import com.capsulecrm.rest.CPerson;
import com.thoughtworks.xstream.XStream;
import play.Play;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mathias Bogaert
 */
public class GoogleCustomSearch extends Controller {
    public static class Annotations {
        public List<Annotation> annotations;

        public Annotations(List<Annotation> annotations) {
            this.annotations = annotations;
        }
    }

    public static class Annotation {
        public String about;
        public Label label;
        public String comment;

        public Annotation(String about, Label label, String comment) {
            this.about = about;
            this.label = label;
            this.comment = comment;
        }
    }

    public static class Label {
        public String name;

        public Label(String name) {
            this.name = name;
        }
    }

    public static Result persons() {
        XStream xstream = new XStream();

        xstream.alias("Annotations", Annotations.class);
        xstream.alias("Annotation", Annotation.class);
        xstream.alias("Label", Label.class);
        xstream.aliasField("Comment", Annotation.class, "comment");

        xstream.addImplicitCollection(Annotations.class, "annotations", Annotation.class);

        xstream.useAttributeFor(Annotation.class, "about");
        xstream.useAttributeFor(Label.class, "name");

        List<Annotation> annotations = new ArrayList<Annotation>();
        for (CPerson person : COrganisation.listAll().get().persons) {
            if (person.firstWebsite() != null) {
                String websiteUrl = person.firstWebsite().url;

                if (websiteUrl != null) {
                    annotations.add(new Annotation(
                            websiteUrl,
                            new Label(Play.application().configuration().getString("gcs.label")),
                            person.getName()));
                }
            }
        }

        // we are hosting the file, to increase the limits, see
        // https://developers.google.com/custom-search/docs/annotations#limits

        response().setContentType("text/xml");
        return ok(xstream.toXML(new Annotations(annotations)));
    }

    public static Result organisations() {
        XStream xstream = new XStream();

        xstream.alias("Annotations", Annotations.class);
        xstream.alias("Annotation", Annotation.class);
        xstream.alias("Label", Label.class);
        xstream.aliasField("Comment", Annotation.class, "comment");

        xstream.addImplicitCollection(Annotations.class, "annotations", Annotation.class);

        xstream.useAttributeFor(Annotation.class, "about");
        xstream.useAttributeFor(Label.class, "name");

        List<Annotation> annotations = new ArrayList<Annotation>();
        for (COrganisation organisation : COrganisation.listAll().get().organisations) {
            if (organisation.firstWebsite() != null) {

                String websiteUrl = organisation.firstWebsite().url;

                if (websiteUrl != null) {
                    annotations.add(new Annotation(
                            websiteUrl,
                            new Label(Play.application().configuration().getString("gcs.label")),
                            organisation.name));
                }
            }
        }

        // we are hosting the file, to increase the limits, see
        // https://developers.google.com/custom-search/docs/annotations#limits

        response().setContentType("text/xml");
        return ok(xstream.toXML(new Annotations(annotations)));
    }
}
