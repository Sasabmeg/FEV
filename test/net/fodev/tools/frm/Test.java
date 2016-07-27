package net.fodev.tools.frm;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.fodev.tools.frm.control.FrameSelector;

public class Test extends Application {

	public static void main(String[] args) {
		launch(args);
	}

    @Override
    public void start(Stage primaryStage) {

        FrameSelector frameSelector = new FrameSelector();
        frameSelector.readHeaderFromFile("f:/Code/FOnline/Fallout_Dat/art/critters/hanpwraa.frm");

        Image[] images = frameSelector.getImagesForAnimation();

        ImageView imageView = new ImageView();
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(imageView.imageProperty(), images[0])),
                new KeyFrame(Duration.seconds(1), new KeyValue(imageView.imageProperty(), images[1])),
                new KeyFrame(Duration.seconds(2), new KeyValue(imageView.imageProperty(), images[2])),
                new KeyFrame(Duration.seconds(3), new KeyValue(imageView.imageProperty(), images[3])),
                new KeyFrame(Duration.seconds(4), new KeyValue(imageView.imageProperty(), images[4])),
                new KeyFrame(Duration.seconds(5), new KeyValue(imageView.imageProperty(), null))
                );
        timeline.play();
        StackPane root = new StackPane();
        root.getChildren().add(imageView);
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }
}