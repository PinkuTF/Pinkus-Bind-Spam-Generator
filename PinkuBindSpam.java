import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.GridPane;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import java.io.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class PinkuBindSpam extends Application
{
	// Gui for bind spam generator
	File inputPath;
	File outputPath; 
	
	public void start(Stage primaryStage)
	{
			
		final FileChooser fileChooser = new FileChooser();
		
		GridPane pane = new GridPane();
		pane.setAlignment(Pos.CENTER);
		pane.setPadding(new Insets( 11.5,12.5,13.5,14.5));
		pane.setHgap(1);
		pane.setVgap(1);
		
		
		pane.add(new Label("Input text file"),0,0);
		TextField input = new TextField();
		pane.add(input,7,0);
    		input.setEditable(false);
		
		Button btChooseInput = new Button("...");
		pane.add(btChooseInput,14,0);
		//Opens file explorer and sets input file to chosen file
		btChooseInput.setOnAction(
		            new EventHandler<ActionEvent>() 
		            {
		                @Override
		                public void handle(final ActionEvent e) 
		                {
		                    File file = fileChooser.showOpenDialog(primaryStage);
		                    if (file != null) 
		                    {
		                        inputPath = file;
		                        try
		                        {
		                        	input.setText(file.getCanonicalPath());
		                        }
		                        catch (IOException ex)
		                        {
		                        	input.setText("File Not Found");
		                        }
		                    }
		                }
		            });
		 
		
		pane.add(new Label("Output text file"),0,7);
		TextField output = new TextField();
		pane.add(output,7,7);
		output.setEditable(false);
		
		Button btChooseOutput = new Button("...");
		pane.add(btChooseOutput,14,7);
		//Opens file explorer and sets output file to chosen file
		btChooseOutput.setOnAction(
	            new EventHandler<ActionEvent>() 
	            {
	                @Override
	                public void handle(final ActionEvent d) 
	                {
	                    File file = fileChooser.showOpenDialog(primaryStage);
	                    if (file != null) 
	                    {
	                        outputPath = file;
	                        try
	                        {
	                        	output.setText(file.getCanonicalPath());
	                        }
	                        catch (IOException ex)
	                        {
	                        	output.setText("File Not Found");
	                        }
	                    }
	                }
	            });
		
		pane.add(new Label("Alias name (must be unique\rwith no spaces)"),0,14);
		TextField aliasName = new TextField();
		pane.add(aliasName,7,14);
		
		pane.add(new Label("Key to bind"),0,21);
		TextField keyBind = new TextField();
		pane.add(keyBind,7,21);
		
		Button btGo = new Button("Go");
		pane.add(btGo,7,28);
		
		Button btHelp = new Button("Help");
		pane.add(btHelp,0,28);
		
		
		// Opens up a stage that explains how the program works to the user
		btHelp.setOnAction(
	            new EventHandler<ActionEvent>() 
	            {
	                @Override
	                public void handle(final ActionEvent r) 
	                {
	                    Stage helpStage = new Stage();	                 
	                    helpStage.setTitle("Help");
	                    GridPane helpPane = new GridPane();
	                    Scene helpScene = new Scene(helpPane);
	                    
	                    helpPane.setAlignment(Pos.TOP_LEFT);
	                    helpPane.setPadding(new Insets( 11.5,12.5,13.5,14.5));
	                    helpPane.setHgap(1);
	                    helpPane.setVgap(1);
	                    
	                    Label helpMsg = new Label("Click on the buttons containing the three dots next to the text fields\r"
	                    		+ "in the input & output sections. Then, select a text file for each\r"
	                    		+ "field from the file explorer. Make sure the output file is a blank new \r"
	                    		+ "file, otherwise it will get over-written. The program will then proceed \r"
	                    		+ "to make a button-mashable bind spam script from the text document\r"
	                    		+ "it was fed, allowing for copy pastas to be spammed in game.\r"
	                    		+ "\r"
	                    		+ "Once the program is done, open the output text file. You should \r"
	                    		+ "see something along the lines of \"bind [ Bindspam0\" for the\r"
	                    		+ "first line of code, and a bunch of aliases for the next lines.\r"
	                    		+ "Copy and paste the whole output into your autoexec.cfg file in\r"
	                    		+ "your tf2 directory.\r"
	                    		+ "\r"
	                    		+ "Make sure that the input on the \"key to bind\" line matches that\r"
	                    		+ "of what source games label their keys. (ex. up arrow would be \"UPARROW\".)\r"
	                    		+ "Also, make sure to not have two binds with the same alias name. This\r"
	                    		+ "causes issues with the binds blending together and getting mixed up\r"
	                    		+ "in game.");
	                    helpPane.add(helpMsg,0,0);
	                    
	                    helpStage.setScene(helpScene);
	                    helpStage.show();
	                                  	                    	                    
	                }
	            });
		
		Label errorMsg = new Label("");
		
		pane.add(errorMsg,0,34);
		
		GridPane.setHalignment(btGo, HPos.RIGHT);
		
		// Checks if the inputs are valid and runs the Spambuilder
		btGo.setOnAction(
	            new EventHandler<ActionEvent>() 
	            {
	                @Override
	                public void handle(final ActionEvent c) 
	                {                   
	                    if (aliasName.getText().length() <= 0)
	                    {
	                    	errorMsg.setText("Error: Alias must have name");
	                    	return;
	                    }
	                    
	                    if (keyBind.getText().length() <= 0)
	                    {
	                    	errorMsg.setText("Error: Key must be chosen");
	                    	return;
	                    }
	                    
	                    if (!inputPath.exists())
	                    {
	                    	errorMsg.setText("Error: nonexistent input file");
	                    	return;
	                    }
	                    
	                    if (!outputPath.exists())
	                    {
	                    	errorMsg.setText("Error: nonexistent output file");
	                    	return;
	                    }
	                    
	                    String extension = "";
	                    
	                    int i = inputPath.getPath().lastIndexOf('.');
	                    if (i > 0)
	                    {
	                    	extension = inputPath.getPath().substring(i+1);                    	
	                    }
	                    
	                    if (!extension.equals("txt"))
	                    {
	                    	errorMsg.setText("Error: input file must be text file");
	                    	return;
	                    }
	                    
	                    i = outputPath.getPath().lastIndexOf('.');
	                    if (i > 0)
	                    {
	                    	extension = outputPath.getPath().substring(i+1);                    	
	                    }
	                    
	                    if (!extension.equals("txt"))
	                    {
	                    	errorMsg.setText("Error: output file must be text file");
	                    	return;
	                    }
	                    	                    
	                    Spambuilder.KEY_Bind = keyBind.getText();
	                    Spambuilder.Alias_Name = aliasName.getText();
	                    Spambuilder.BindAlias = aliasName.getText() + "_Bind";
	                    Spambuilder.file = inputPath;
	                    Spambuilder.outFile = outputPath;
	                    Spambuilder.buildBind(new SimpleList<String>());
	                    
	                    errorMsg.setText(aliasName.getText() + " finished");
	                    	                           
	                }
	            });
		
		Scene scene = new Scene(pane);
		primaryStage.setTitle("Pinku's Bind Spam Generator");
		primaryStage.setScene(scene);
		primaryStage.show();
					
	}
	
	public static void main(String[] args)
	{
		Application.launch(args);
	}
}
