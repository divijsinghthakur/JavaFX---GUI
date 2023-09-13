// Assignment #: Arizona State University Spring 2023 CSE205 #6
//         Name: Divij Singh Thakur	
//    StudentID: 1226929849
//      Lecture: TTh 10:30AM
//  Description: //-- is where you should add your own code

//Note: when you submit on gradescope, you need to comment out the package line
//package yourPackageName;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.SubScene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.control.TextArea;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;

public class CoursePane extends HBox
{
    //GUI components
    private ArrayList<Course> courseList;
    private VBox checkboxContainer;

    //Step 1.1: declare any necessary instance variables here
    //----
    private Label subject, courseNum, Instructor, courseEnrolled, errorMessage;
    private Button add, drop;
    private TextField textfield1, textfield2;
    private ComboBox<String> cb;
    private CheckBox chco;
    private String k, t;
    private Course course;

    //constructor
    public CoursePane()
    {
        //step 1.2: initialize instance variables
        //----
    	course = new Course();
    	
    	Label labelLeft = new Label("Add Course(s)");
        labelLeft.setTextFill(Color.BLUE);
        labelLeft.setFont(Font.font(null, 14));

        Label labelRight = new Label("Course(s) Enrolled");
        labelRight.setTextFill(Color.BLUE);
        labelRight.setFont(Font.font(null, 14));

        //set up the layout. Note: CoursePane is a HBox and contains
        //leftPane, centerPane and rightPane. Pick proper layout class
        //and use nested sub-pane if necessary, then add each GUI components inside.

        //step 1.3: create and set up the layout of the leftPane, leftPane contains a top label, the center sub-pane
        //and a label show at the bottom
        //----
        GridPane leftPane = new GridPane();//setting up the left pane as a gridpane
        this.getChildren().add(leftPane);
        leftPane.getChildren().add(labelLeft); 
        leftPane.setVgap(1.5);
        leftPane.setStyle("-fx-border-color: black;");
        leftPane.setPadding(new Insets(20, 20, 20, 20));
        
        subject = new Label("Subject");//creating label for the combobox
        leftPane.add(subject, 0, 50);
        subject.setAlignment(Pos.CENTER);
        
        cb = new ComboBox<>();//adding the combobox
        cb.getItems().addAll("ACC", "AME", "BME", "CHM", "CSE", "DAT", "EEE");
        cb.setValue("CSE");
        leftPane.add(cb, 10, 50);
        			
        courseNum = new Label("Course Num");//creating label for courseNum
        leftPane.add(courseNum, 0, 70);
        courseNum.setAlignment(Pos.CENTER);
        
        textfield1 = new TextField();//creating a textfield to input the values
        leftPane.add(textfield1, 10,70);
        textfield1.setPrefSize(getLayoutX(), getLayoutY());
        
        
        Instructor = new Label("Instructor");//creating a label for instructor
        leftPane.add(Instructor, 0, 90);
        Instructor.setAlignment(Pos.CENTER);
        
        textfield2 = new TextField();//creating a textfield to input the values
        errorMessage = new Label("No course entered");
        leftPane.add(errorMessage, 0,110);
        leftPane.add(textfield2, 10,90);
        textfield2.setPrefSize(getLayoutX(), getLayoutY());;
        
        //step 1.4: create and set up the layout of the centerPane which holds the two buttons
		//----
        GridPane centerPane = new GridPane();//setting up the center pane as a gridpane
        this.getChildren().add(centerPane);
        centerPane.setAlignment(Pos.CENTER);
        centerPane.setVgap(0.5);
        
        add = new Button("Add =>");//creating the add button
        centerPane.add(add, 60,120);
      
        drop = new Button("Drop <=");//creating the drop button
        centerPane.add(drop, 60, 150);
        centerPane.setPadding(new Insets(20, 20, 20, 20));

        //step 1.5: create and set up the layout of the rightPane, rightPane contains a top label,
        //checkboxContainer and a label show at the bottom
        //----
        
        VBox rightPane = new VBox();//setting up the right pane as a vbox
        this.getChildren().add(rightPane);
        rightPane.getChildren().add(labelRight);
        labelRight.setAlignment(Pos.TOP_RIGHT);
        rightPane.setPadding(new Insets(20, 20, 20, 20));
        rightPane.setStyle("-fx-border-color: black;");
        
        checkboxContainer = new VBox();
        courseList = new ArrayList<Course>(); 
        rightPane.getChildren().add(checkboxContainer);
        
        courseEnrolled = new Label("Total course enrolled: 0");//creating a label to track the number of courses enrolled
        courseEnrolled.setTranslateX(0);
        courseEnrolled.setTranslateY(320);
        rightPane.getChildren().add(courseEnrolled);
        
        //CoursePane is a HBox. Add leftPane, centerPane and rightPane inside
        this.setPadding(new Insets(10, 10, 10, 10));
        
        //Step 3: Register the GUI component with its handler class
        //----
        add.setOnAction(new ButtonHandler());
        drop.setOnAction(new ButtonHandler());
        cb.setOnAction(new ComboBoxHandler());
       

    } //end of constructor

    //step 2.1: Whenever a new Course is added or one or several courses are dropped/removed, this method will
    //1) clear the original checkboxContainer;
    //2) create a CheckBox for each Course object inside the courseList, and also add it inside the checkboxContainer;
    //3) register the CheckBox with the CheckBoxHandler.
    public void updateCheckBoxContainer()
    {
        checkboxContainer.getChildren().clear();//clearing the container
        
        for (int i = 0; i < courseList.size(); i++) {//creating a checkbox for each course object
        	chco = new CheckBox(courseList.get(i).toString());
        	
        	chco.setOnAction(new CheckBoxHandler(courseList.get(i)));//registering the checkbox with the handler
        	checkboxContainer.getChildren().add(chco);
        }
  
    }

    //Step 2.2: Create a ButtonHandler class
    private class ButtonHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent e)
        {
            Object source = e.getSource();
            Boolean isNew = true;//creating a boolean to check if the course entered is duplicate or not

            try {
					if (source == add && !textfield1.getText().equals("") && !textfield2.getText().equals(""))//everything is entered correctly and the "Add =>" button is pressed
              	    {
						t = textfield2.getText();
						int y = Integer.parseInt(textfield1.getText());//need to check whether the course already exist inside the courseList or not
						
                	   for (int i = 0; i < courseList.size(); i++) {
                		   
                		   if (cb.getValue().equals(courseList.get(i).getSubject()) && y == courseList.get(i).getCourseNum()) {
                			   isNew = false;
                		   }
                		   else {
                			   isNew = true;
                		   }
                	   }
                	   if (isNew)//it's a new course)
                	   {
                		   course = new Course(k, y, t);
                		   courseList.add(course);
                		   updateCheckBoxContainer();
                		   courseEnrolled.setText("Total course enrolled: " + courseList.size());
						   errorMessage.setText("Course added successfully");
						   errorMessage.setTextFill(Color.BLACK);
					   }
					   else //a duplicated one
					   {
						   //show error message
						   errorMessage.setText("Duplicated course - Not Added");
						   errorMessage.setTextFill(Color.RED);
					   }
                	   textfield1.clear();
                       textfield2.clear();

                    }
            

                   //Clear all the text fields and prepare for the next entry;
                   

                   else if (source == add && (textfield1.getText().equals("") || textfield2.getText().equals(""))) //">=" button is pressed, but one of the text field is empty
                  {
                    errorMessage.setText("At least one field is empty. Fill all fields");
                    errorMessage.setTextFill(Color.RED);
               	  }

                else if (source == drop) //when "Drop Course" button is pushed.)
                {
                	courseEnrolled.setText("Total course enrolled: " + courseList.size());
                    updateCheckBoxContainer();
                }
                else  //  for all other invalid actions, thrown an exception and it will be caught
                {
					//----
				}

            }
        //end of try

            catch(NumberFormatException ex)
            {
            	errorMessage.setText("Error! Course number must be an integer");
            	errorMessage.setTextFill(Color.RED);
            }
            catch(Exception exception)
            {
            	errorMessage.setText("This is an error");
            	errorMessage.setTextFill(Color.RED);
            }
        } //end of handle() method
    } //end of ButtonHandler class

    ////Step 2.3: A ComboBoxHandler
    private class ComboBoxHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent e) {
        	
			k = cb.getValue();
			
		}

	
    }//end of ComboBoxHandler

    //Step 2.4: A CheckBoxHandler
    private class CheckBoxHandler implements EventHandler<ActionEvent>
    {
        // Pass in a Course object so that we know which course is associated with which CheckBox
        private Course oneCourse;

        //constructor
        public CheckBoxHandler(Course oneCourse)
        {
           this.oneCourse = oneCourse;
        }
        public void handle(ActionEvent e)
        {
        	
        	Object source = e.getSource();
        	
		if (((CheckBox) source).isSelected()) {//parsing the source to checkbox to make sure that source is not considered as a button by the program
        	   courseList.remove(oneCourse);
        	   
           }
        }
    }//end of CheckBoxHandler

} //end of CoursePane class