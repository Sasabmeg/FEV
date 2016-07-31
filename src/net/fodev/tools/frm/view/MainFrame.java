package net.fodev.tools.frm.view;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.animation.Animation.Status;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import net.fodev.tools.frm.control.FrameSelector;
import net.fodev.tools.frm.control.FrmExporter;
import net.fodev.tools.frm.control.FrmFileSelector;
import net.fodev.tools.frm.control.FrmUtils;

public class MainFrame extends Application {
	private FrmFileSelector fileSelector;
	private FrameSelector frameSelector;
	private Scene scene;
	private GridPane root;
	private GridPane fileSelectionGrid;
	private GridPane frameControlsGrid;
	private GridPane frameExportGrid;
	private BorderedTitledPane fileSelectionGroup;
	private BorderedTitledPane frameViewGroup;
	private BorderedTitledPane frameControlGroup;
	private BorderedTitledPane frameExportGroup;
	private Pane frameViewContainer;
	private ImageView frameView;
	private CheckBox imageBackgroundCheckBox;
	private Button directionButton[] = new Button[6];
	private Button firstFrameButton;
	private Button lastFrameButton;
	private Button nextFrameButton;
	private Button prevFrameButton;
	private Button playAnimationFrameButton;
	private Button stopAnimationButton;
	private Button nextFileButton;
	private Button prevFileButton;
	private Button selectFileButton;
	private Button fileMaskButton;
	private Button exportFrameButton;
	private Button exportAnimationButton;
	private Button exportPaletteButton;
	private Label filePathLabel;
	private Label fileMaskLabel;
	private TextArea frameInfoText;
	private TextField fileMaskInput;
	private Image image;
	private Timeline animation;
	private boolean autoAnimation = false;
	private double zoomFactor = 1;
	private final double minZoomValue = 0.25;
	private final double maxZoomValue = 5;
	private final double zoomStep = 1.125;

	@Override
	public void start(Stage primaryStage) {
		try {
			setFileSelector();
			setFrameSelector();
			setWindowProperties(primaryStage);
			setRoot();
			setScene(primaryStage);

			addFileSelectionControls(primaryStage);
			addFrameControls();

			addFrameView();
			addFrameInfoTextArea();

			addFrameExportControls(primaryStage);

			addKeybinds();
			addMousebinds();

			showCurrentFrame(true);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void addFrameExportControls(Stage primaryStage) {
		frameExportGrid = new GridPane();
		frameExportGrid.setPrefWidth(600);
		frameExportGrid.setHgap(5);
		frameExportGrid.setVgap(5);
		frameExportGrid.setPadding(new Insets(10, 5, 5, 5));
		frameExportGrid.setGridLinesVisible(false);
		frameExportGrid.setAlignment(Pos.CENTER);
		GridPane.setConstraints(frameExportGrid, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER);
		GridPane.setHalignment(frameExportGrid, HPos.CENTER);

		frameExportGroup = new BorderedTitledPane("Frame export", frameExportGrid);
		Label label = (Label) frameExportGroup.getChildren().get(0);
		label.setStyle(
				"-fx-translate-x:  8; -fx-translate-y: -9; -fx-padding: 0 0 0 4; -fx-background-color: -fx-background;");

		frameExportGroup.setStyle(
				"-fx-content-display: top; -fx-border-color:black; -fx-border-insets: 8 3 3 3; -fx-border-width:1;");
		GridPane.setConstraints(frameExportGroup, 1, 2, 1, 1);

		addExportSingleFrameButton(primaryStage);
		addExportAnimationButton(primaryStage);

		exportPaletteButton = new Button("Exp palette");
		exportPaletteButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				FileChooser fileChooser = new FileChooser();
				FileChooser.ExtensionFilter extPngFilter = new FileChooser.ExtensionFilter(
						"Portable Network Graphics files (*.png)", "*.png");
				FileChooser.ExtensionFilter extBmpFilter = new FileChooser.ExtensionFilter("Bitmap files (*.bmp)",
						"*.bmp");
				FileChooser.ExtensionFilter extGifFilter = new FileChooser.ExtensionFilter(
						"Graphics Intercahnge Format files (*.gif)", "*.gif");
				FileChooser.ExtensionFilter extPalFilter = new FileChooser.ExtensionFilter(
						"Fallout palette file (*.pal)", "*.pal");
				fileChooser.getExtensionFilters().add(extBmpFilter);
				fileChooser.getExtensionFilters().add(extPngFilter);
				fileChooser.getExtensionFilters().add(extGifFilter);
				fileChooser.getExtensionFilters().add(extPalFilter);
				//	TODO: Merge that if and command into command, and throw exceptions or solve null pointer other way
				if (!fileSelector.isFileListEmpty()) {
					Path p = Paths.get(fileSelector.getCurrentExportFolder());
					try {
						fileChooser.setInitialDirectory(p.toFile());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				try {
					File file = fileChooser.showSaveDialog(primaryStage);
					if (file != null) {
						fileSelector.setCurrentExportFolder(file.getParent());
						//	TODO: Add switch here to save palette as a FoPalette or a regular file with the colors.
						if (FrmUtils.getFileExtension(file.toString()).equals("pal")) {
							FrmExporter.exportFoPaletteIntoPng(file.toString());
						} else {
							FrmExporter.exportDefaultFoPaletteIntoPng(file.toString());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

		});
		GridPane.setConstraints(exportPaletteButton, 0, 1, 2, 1, HPos.CENTER, VPos.CENTER);
		frameExportGrid.getChildren().add(exportPaletteButton);

		root.getChildren().add(frameExportGroup);
	}

	private void addExportAnimationButton(Stage primaryStage) {
		exportAnimationButton = new Button("Exp anim");
		exportAnimationButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				DirectoryChooser directoryChooser = new DirectoryChooser();
				Path p = Paths.get(fileSelector.getCurrentExportFolder());
				try {
					directoryChooser.setInitialDirectory(p.toFile());
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					File file = directoryChooser.showDialog(primaryStage);
					if (file != null) {
						fileSelector.setCurrentExportFolder(file.toString());
						String fileName = fileSelector.getCurrentFileName();
						FrmExporter.exportAnimationToFile(frameSelector.getHeader(), file.toString(), fileName, frameSelector.isHasBackground());
						// FrmExporter.exportSingleFrameToFile(frameSelector.getCurrentFrame(),
						// frameSelector.getCurrentFrameIndex(),
						// file.toString());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		GridPane.setConstraints(exportAnimationButton, 1, 0, 1, 1, HPos.CENTER, VPos.CENTER);
		frameExportGrid.getChildren().add(exportAnimationButton);
	}

	private void addExportSingleFrameButton(Stage primaryStage) {
		exportFrameButton = new Button("Exp single frame");
		exportFrameButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				FileChooser fileChooser = new FileChooser();
				FileChooser.ExtensionFilter extPngFilter = new FileChooser.ExtensionFilter(
						"Portable Network Graphics files (*.png)", "*.png");
				FileChooser.ExtensionFilter extBmpFilter = new FileChooser.ExtensionFilter("Bitmap files (*.bmp)",
						"*.bmp");
				FileChooser.ExtensionFilter extGifFilter = new FileChooser.ExtensionFilter(
						"Graphics Intercahnge Format files (*.gif)", "*.gif");
				fileChooser.getExtensionFilters().add(extBmpFilter);
				fileChooser.getExtensionFilters().add(extPngFilter);
				fileChooser.getExtensionFilters().add(extGifFilter);
				if (!fileSelector.isFileListEmpty()) {
					Path p = Paths.get(fileSelector.getCurrentExportFolder());
					try {
						fileChooser.setInitialDirectory(p.toFile());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				try {
					File file = fileChooser.showSaveDialog(primaryStage);
					if (file != null) {
						fileSelector.setCurrentExportFolder(file.getParent());
						FrmExporter.exportSingleFrameToFile(frameSelector.getCurrentFrame(),
								frameSelector.getCurrentFrameIndex(), file.toString(), frameSelector.isHasBackground());
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		GridPane.setConstraints(exportFrameButton, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER);
		frameExportGrid.getChildren().add(exportFrameButton);
	}

	private void addMousebinds() {
		scene.setOnScroll(new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent event) {
				double deltaY = event.getDeltaY();
				if (deltaY < 0) {
					zoomOut();
				} else {
					zoomIn();
				}
				event.consume();
				try {
					showCurrentFrame(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private void setFrameSelector() {
		frameSelector = new FrameSelector();
		frameSelector.setHasBackground(false);
	}

	private void setFileSelector() {
		fileSelector = new FrmFileSelector();
		setDefaultFileList();
	}

	private void setDefaultFileList() {
		fileSelector.setCurrentFolder("f:/Code/FOnline/Fallout_Dat/art/critters");
		fileSelector.setCurrentExportFolder("f:/Code/FOnline/Fallout_Dat/art/critters");
	}

	private void addFileSelectButton(Stage primaryStage) {
		selectFileButton = new Button("Select file");
		selectFileButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				FileChooser fileChooser = new FileChooser();
				FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("FRM files (*.frm)", "*.frm");
				fileChooser.getExtensionFilters().add(extFilter);
				if (!fileSelector.isFileListEmpty()) {
					Path p = Paths.get(fileSelector.getCurrentFolder());
					try {
						fileChooser.setInitialDirectory(p.toFile());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				try {
					File file = fileChooser.showOpenDialog(primaryStage);
					if (file != null) {
						fileSelector.setCurrentFolder(file.getParent());
						fileSelector.setCurrentFile(file.getName());
						showCurrentFrame(true);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		GridPane.setConstraints(selectFileButton, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER);
		fileSelectionGrid.getChildren().add(selectFileButton);
	}

	private void addFileSelectionControls(Stage primaryStage) {
		addFileSelectionGrid();
		addGroupBox();
		addFilePathAndNameLabel();
		addFileSelectButton(primaryStage);
		addPrevButton();
		addNextButton();
		addFileMaskButton();
		addFileMaskText();
		addFileMaskLabel();
	}

	private void addFileMaskLabel() {
		fileMaskLabel = new Label("Select a frame file.");
		GridPane.setConstraints(fileMaskLabel, 3, 1, 1, 1, HPos.LEFT, VPos.CENTER);
		fileSelectionGrid.getChildren().add(fileMaskLabel);
	}

	private void addFileMaskText() {
		fileMaskInput = new TextField("*.frm");
		fileMaskInput.setEditable(true);
		fileMaskInput.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ENTER) {
					fileMaskButton.fire();
					fileMaskButton.requestFocus();
				}
			}
		});
		GridPane.setConstraints(fileMaskInput, 1, 1, 2, 1, HPos.LEFT, VPos.CENTER);
		fileSelectionGrid.getChildren().add(fileMaskInput);
	}

	private void addFileMaskButton() {
		fileMaskButton = new Button("Set file mask");
		fileMaskButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				fileSelector.setFileMask(fileMaskInput.getText());
				updateFileMaskLabel();
				if (!fileSelector.isCurrentFileMatchingPattern()) {
					fileSelector.firstMatchingPattern();
					try {
						showCurrentFrame(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});
		GridPane.setConstraints(fileMaskButton, 0, 1, 1, 1, HPos.CENTER, VPos.CENTER);
		fileSelectionGrid.getChildren().add(fileMaskButton);
	}

	private void addGroupBox() {
		fileSelectionGroup = new BorderedTitledPane("File selection", fileSelectionGrid);
		Label label = (Label) fileSelectionGroup.getChildren().get(0);
		label.setStyle(
				"-fx-translate-x:  8; -fx-translate-y: -9; -fx-padding: 0 0 0 4; -fx-background-color: -fx-background;");

		fileSelectionGroup.setStyle(
				"-fx-content-display: top; -fx-border-color:black; -fx-border-insets: 8 3 3 3; -fx-border-width:1;");
		GridPane.setConstraints(fileSelectionGroup, 0, 0, 2, 1);
		root.getChildren().add(fileSelectionGroup);
	}

	private void addFileSelectionGrid() {
		fileSelectionGrid = new GridPane();
		fileSelectionGrid.setPrefWidth(600);
		fileSelectionGrid.setHgap(5);
		fileSelectionGrid.setVgap(5);
		fileSelectionGrid.setPadding(new Insets(10, 5, 5, 5));
		fileSelectionGrid.setGridLinesVisible(false);
		fileSelectionGrid.setAlignment(Pos.CENTER_LEFT);
		GridPane.setConstraints(fileSelectionGrid, 0, 0, 1, 1, HPos.LEFT, VPos.CENTER);
		GridPane.setHalignment(fileSelectionGrid, HPos.LEFT);
	}

	private void setWindowProperties(Stage primaryStage) {
		primaryStage.setMinWidth(640);
		primaryStage.setMinHeight(480);
		primaryStage.setMaxWidth(1024);
		primaryStage.setMaxHeight(768);
		primaryStage.setTitle("FOnline Frame Viewer");
	}

	private void addFrameControls() {

		frameControlsGrid = new GridPane();
		frameControlsGrid.setPrefWidth(300);
		frameControlsGrid.setHgap(5);
		frameControlsGrid.setVgap(5);
		frameControlsGrid.setPadding(new Insets(10, 5, 5, 5));
		frameControlsGrid.setGridLinesVisible(false);
		frameControlsGrid.setAlignment(Pos.BASELINE_CENTER);
		GridPane.setConstraints(frameControlsGrid, 0, 0, 1, 1, HPos.CENTER, VPos.CENTER);

		for (int i = 0; i < 6; i++) {
			directionButton[i] = new Button();
			directionButton[i].setText("Dir " + i);
			directionButton[i].prefWidth(50);
			directionButton[i].prefHeight(5);
			final int finalIndex = i;
			directionButton[i].setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					frameSelector.setDirection(finalIndex);
					try {
						showCurrentFrame(true);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			});
			GridPane.setConstraints(directionButton[i], i, 1, 1, 1, HPos.CENTER, VPos.CENTER);
			frameControlsGrid.getChildren().add(directionButton[i]);
		}

		firstFrameButton = new Button("First frame");
		firstFrameButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				autoAnimation = false;
				frameSelector.first();
				try {
					showCurrentFrame(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		GridPane.setConstraints(firstFrameButton, 0, 0);
		frameControlsGrid.getChildren().add(firstFrameButton);

		prevFrameButton = new Button("Prev frame");
		prevFrameButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				autoAnimation = false;
				frameSelector.prev();
				try {
					showCurrentFrame(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		GridPane.setConstraints(prevFrameButton, 1, 0);
		frameControlsGrid.getChildren().add(prevFrameButton);

		playAnimationFrameButton = new Button("Play anim");
		playAnimationFrameButton.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				autoAnimation = true;
				playAnimation();
			}
		});
		GridPane.setConstraints(playAnimationFrameButton, 2, 0);
		frameControlsGrid.getChildren().add(playAnimationFrameButton);

		stopAnimationButton = new Button("Stop anim");
		stopAnimationButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				autoAnimation = false;
				stopAnimation();
			}
		});
		GridPane.setConstraints(stopAnimationButton, 3, 0);
		frameControlsGrid.getChildren().add(stopAnimationButton);

		nextFrameButton = new Button("Next frame");
		nextFrameButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				autoAnimation = false;
				frameSelector.next();
				try {
					showCurrentFrame(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		GridPane.setConstraints(nextFrameButton, 4, 0);
		frameControlsGrid.getChildren().add(nextFrameButton);

		lastFrameButton = new Button("Last frame");
		lastFrameButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				autoAnimation = false;
				frameSelector.last();
				try {
					showCurrentFrame(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		GridPane.setConstraints(lastFrameButton, 5, 0);
		frameControlsGrid.getChildren().add(lastFrameButton);

	    imageBackgroundCheckBox = new CheckBox("Image background");
	    imageBackgroundCheckBox.selectedProperty().addListener(new ChangeListener<Boolean>() {
	        @Override
	    	public void changed(ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) {
	        	frameSelector.setHasBackground(new_val);
	        	try {
					showCurrentFrame(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
	        }
	    });
		GridPane.setConstraints(imageBackgroundCheckBox, 0, 2, 3, 1);
		frameControlsGrid.getChildren().add(imageBackgroundCheckBox);

		frameControlGroup = new BorderedTitledPane("Frame control", frameControlsGrid);
		Label label = (Label) frameControlGroup.getChildren().get(0);
		label.setStyle(
				"-fx-translate-x:  8; -fx-translate-y: -9; -fx-padding: 0 0 0 4; -fx-background-color: -fx-background;");

		frameControlGroup.setStyle(
				"-fx-content-display: top; -fx-border-color:black; -fx-border-insets: 8 3 3 3; -fx-border-width:1;");
		GridPane.setConstraints(frameControlGroup, 0, 2, 1, 1, HPos.CENTER, VPos.CENTER);
		frameControlGroup.getChildren().add(frameControlsGrid);

		root.getChildren().add(frameControlGroup);
	}

	private void addPrevButton() {
		prevFileButton = new Button();
		prevFileButton.setText("Prev file");
		prevFileButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stopAnimation();
				fileSelector.prev();
				frameSelector.first();
				try {
					showCurrentFrame(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		GridPane.setConstraints(prevFileButton, 1, 0, 1, 1, HPos.LEFT, VPos.CENTER);
		fileSelectionGrid.getChildren().add(prevFileButton);
	}

	private void addNextButton() {
		nextFileButton = new Button();
		nextFileButton.setText("Next file");
		nextFileButton.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				stopAnimation();
				fileSelector.next();
				frameSelector.first();
				try {
					showCurrentFrame(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		GridPane.setConstraints(nextFileButton, 2, 0, 1, 1, HPos.RIGHT, VPos.CENTER);
		fileSelectionGrid.getChildren().add(nextFileButton);
	}

	private void addFrameInfoTextArea() {
		frameInfoText = new TextArea();
		frameInfoText.setMaxSize(300, 800);
		frameInfoText.setMinSize(100, 100);
		frameInfoText.setPrefSize(200, 500);
		frameInfoText.setEditable(false);

		StackPane textAreaContainer;
		textAreaContainer = new StackPane();
		textAreaContainer.setPadding(new Insets(8, 5, 3, 3));
		textAreaContainer.getChildren().add(frameInfoText);

		GridPane.setConstraints(textAreaContainer, 1, 1, 1, 1, HPos.CENTER, VPos.CENTER);
		root.getChildren().add(textAreaContainer);
	}

	private void addFilePathAndNameLabel() {
		filePathLabel = new Label("Select a frame file.");
		GridPane.setConstraints(filePathLabel, 3, 0, 1, 1, HPos.LEFT, VPos.CENTER);
		fileSelectionGrid.getChildren().add(filePathLabel);
	}

	private void addKeybinds() {
		scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
			public void handle(final KeyEvent keyEvent) {
				if (keyEvent.getCode() == KeyCode.UP) {
					nextFrameButton.fire();
					keyEvent.consume();
				}
				if (keyEvent.getCode() == KeyCode.DOWN) {
					prevFrameButton.fire();
					keyEvent.consume();
				}
				if (keyEvent.getCode() == KeyCode.RIGHT) {
					nextFileButton.fire();
					keyEvent.consume();
				}
				if (keyEvent.getCode() == KeyCode.LEFT) {
					prevFileButton.fire();
					keyEvent.consume();
				}
				if (keyEvent.getCode() == KeyCode.BACK_QUOTE) {
					directionButton[0].fire();
					keyEvent.consume();
				}
				if (keyEvent.getCode() == KeyCode.DIGIT6) {
					directionButton[0].fire();
					keyEvent.consume();
				}
				if (keyEvent.getCode() == KeyCode.DIGIT0) {
					directionButton[0].fire();
					keyEvent.consume();
				}
				if (keyEvent.getCode() == KeyCode.DIGIT1) {
					directionButton[1].fire();
					keyEvent.consume();
				}
				if (keyEvent.getCode() == KeyCode.DIGIT2) {
					directionButton[2].fire();
					keyEvent.consume();
				}
				if (keyEvent.getCode() == KeyCode.DIGIT3) {
					directionButton[3].fire();
					keyEvent.consume();
				}
				if (keyEvent.getCode() == KeyCode.DIGIT4) {
					directionButton[4].fire();
					keyEvent.consume();
				}
				if (keyEvent.getCode() == KeyCode.DIGIT5) {
					directionButton[5].fire();
					keyEvent.consume();
				}

			}
		});
	}

	private void showCurrentFrame(boolean reloadImage) throws Exception {
		stopAnimation();
		if (reloadImage) {
			frameSelector.readHeaderFromFile(fileSelector.getCurrentFileNameAndPath());
			image = frameSelector.getImage();
		}
		scaleImageViewForImage(image.getWidth(), image.getHeight());

		frameView.setImage(image);
		if (autoAnimation) {
			playAnimation();
		}
		updatePathLabel();
		updateFileMaskLabel();
		updateFrameInfoText();
	}

	private void scaleImageViewForImage(double width, double height) {
		width = width * zoomFactor;
		height = height * zoomFactor;
		double containerWidth = frameViewContainer.getWidth() - 20 > 0 ? frameViewContainer.getWidth()
				: frameViewContainer.getPrefWidth();
		double containerHeight = frameViewContainer.getHeight() - 20 > 0 ? frameViewContainer.getHeight()
				: frameViewContainer.getPrefHeight();
		containerWidth -= 20;
		containerHeight -= 20;

		double widthScale = containerWidth / width;
		double heightScale = containerHeight / height;
		double scale = widthScale < heightScale ? widthScale : heightScale;
		if (scale < 1) {
			frameView.setScaleX(1);
			frameView.setScaleY(1);
			if (widthScale < heightScale) {
				frameView.setFitWidth(containerWidth);
			} else {
				frameView.setFitHeight(containerHeight);
			}
		} else {
			frameView.setFitWidth(0);
			frameView.setFitHeight(0);
			frameView.setScaleX(zoomFactor);
			frameView.setScaleY(zoomFactor);
		}
	}

	private void updateFrameInfoText() {
		String text = "Header:" + System.lineSeparator() + System.lineSeparator() + frameSelector.getHeaderInfo()
				+ System.lineSeparator() + System.lineSeparator();
		text += "Current frame: " + System.lineSeparator() + System.lineSeparator();
		text += "Index: " + frameSelector.getCurrentFrameIndex() + System.lineSeparator();
		text += "Direction: " + frameSelector.getDirection() + System.lineSeparator();
		text += "Offset index: " + frameSelector.getFrameIndex() + System.lineSeparator();
		text += "Offset: " + frameSelector.getFrameOffsetIndex() + System.lineSeparator();
		text += frameSelector.getFrameInfo();
		frameInfoText.setText(text);
	}

	private void updatePathLabel() throws Exception {
		filePathLabel.setText(fileSelector.getCurrentFileNameAndPath());
	}

	private void updateFileMaskLabel() {
		fileMaskLabel.setText(fileSelector.getMatchingFilesInList() + " / " + fileSelector.getTotalFilesInList());
	}

	private void setScene(Stage primaryStage) {
		scene = new Scene(root, 800, 600);
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	private void setRoot() {
		root = new GridPane();
		root.setPrefSize(800, 600);
		root.setGridLinesVisible(false);
		ColumnConstraints frameColumnConstraint = new ColumnConstraints(200, 400, 800);
		ColumnConstraints textInfoColumnConstraint = new ColumnConstraints(100, 200, 300);
		RowConstraints frameRowConstraint = new RowConstraints(200, 500, 1000);
		frameColumnConstraint.setHgrow(Priority.SOMETIMES);
		textInfoColumnConstraint.setHgrow(Priority.SOMETIMES);
		frameRowConstraint.setVgrow(Priority.SOMETIMES);
		root.getColumnConstraints().addAll(frameColumnConstraint, textInfoColumnConstraint);
		root.getRowConstraints().addAll(new RowConstraints(), frameRowConstraint);
	}

	private void addFrameView() {

		frameView = new ImageView();
		frameView.setPreserveRatio(true);
		frameView.setSmooth(true);
		frameView.setCache(false);

		frameViewContainer = new Pane();
		frameViewContainer.getChildren().add(frameView);
		frameViewContainer.setPrefSize(496, 471);

		frameViewGroup = new BorderedTitledPane("Frame view", frameViewContainer);
		frameViewGroup.setPrefSize(496, 471);
		Label label = (Label) frameViewGroup.getChildren().get(0);
		label.setStyle(
				"-fx-translate-x:  8; -fx-translate-y: -9; -fx-padding: 0 0 0 4; -fx-background-color: -fx-background;");

		frameViewGroup.setStyle(
				"-fx-content-display: top; -fx-border-color:black; -fx-border-insets: 8 3 3 3; -fx-border-width:1;");
		GridPane.setConstraints(frameViewGroup, 0, 1, 1, 1, HPos.CENTER, VPos.CENTER);

		frameViewGroup.getChildren().add(frameView);

		scene.widthProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth,
					Number newSceneWidth) {
				try {
					showCurrentFrame(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		scene.heightProperty().addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight,
					Number newSceneHeight) {
				try {
					showCurrentFrame(false);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});

		root.getChildren().add(frameViewGroup);
	}

	private void playAnimation() {
		if (isAnimationPlaying()) {
			stopAnimation();
		} else {
			try {
				Image[] images = frameSelector.getImagesForAnimation();
				animation = new Timeline();
				int duration = 1000 / frameSelector.getFramesPerSecond();
				for (int i = 0; i < frameSelector.getFramesPerDirection(); i++) {
					animation.getKeyFrames().add(new KeyFrame(Duration.millis(i * duration),
							new KeyValue(frameView.imageProperty(), images[i])));
				}
				animation.setCycleCount(Timeline.INDEFINITE);
				scaleImageViewForAnimation(images);
				animation.play();
			} catch (Exception ex) {
				ex.printStackTrace();
				animation.stop();
			}
		}
	}

	private void scaleImageViewForAnimation(Image[] images) {
		int maxHeight = 0;
		int maxWidth = 0;
		for (Image i : images) {
			if (maxWidth < i.getWidth()) {
				maxWidth = (int) i.getWidth();
			}
			if (maxHeight < i.getHeight()) {
				maxHeight = (int) i.getHeight();
			}
		}
		scaleImageViewForImage(maxWidth, maxHeight);
	}

	private void stopAnimation() {
		if (isAnimationPlaying()) {
			animation.stop();
		}
	}

	private boolean isAnimationPlaying() {
		if (animation != null && animation.getStatus() == Status.RUNNING) {
			return true;
		} else {
			return false;
		}
	}

	public void zoomIn() {
		if (zoomFactor > minZoomValue) {
			zoomFactor /= zoomStep;
		}
	}

	public void zoomOut() {
		if (zoomFactor < maxZoomValue) {
			zoomFactor *= zoomStep;
		}
		normalizeZoom();
	}

	private void normalizeZoom() {
		if (Math.abs(zoomFactor - 1) < zoomStep / 10) {
			zoomFactor = 1;
		}
	}

	public void setDefaultZoom() {
		zoomFactor = 1;
	}

	public static void main(String[] args) {
		launch(args);
	}
}
