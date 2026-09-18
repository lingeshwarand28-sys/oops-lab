import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scence.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.sql.*;
public class StudentManagementApp extends Application{
    private Connection connection;
    private TextField idField=new TextField();
    private TextField nameField= new TextField();
    private TextField ageField=newTextField();
    private TextField courseField=newTextField();
    private TextField displayArea=newTextField();
    public static void main(String[] args) {
        launch(args);
    }
    public void start(Stage primaryStage){
        connectToDatabase();
        GridPane gridPane=new GridPane();
        gridPane.setPadding(new Insets(10));
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        Font font=new Font("Arial",14);
        idField.setPromptText("ID(for Update/Delete)");
        idField.setFont(font);
        nameField.setPromptText("Name");
        nameField.setFont(font);
        ageField.setPromptText("Age");
        ageField.setFont(font);
        courseField.setPromptText("Course");
        courseField.setFont(font);
        Button createButton=new Button("Create");
        createButton.setFont(font);
        createButton.setStyle("-fx-background-color:#4CAF50;-fx-text-fill:white;");

        createButton.setOnAction(e->createStudent());
        Button readButton=new Button("Display");
        readButton.setFont(font);
        readButton.setStyle("-fx-background-color:#2196F3;-fx-text-fil:white;");

        readButton.setOnAction(e->readStudents());
        Button updateButton=new Button("Update");
        updateButton.setFont(font);
        updateButton.setStyle("-fx-background-color:#FF9800;-fx-text-fill:white;");

        updateButton.setOnAction(e->updteStudent());
        Button deleteButton=new Button("Delete");
        deleteButton.setFont(font);
        deleteButton.setStyle("-fx-background-color:#F44336;-fx-txt-fill;white;");

        deleteButton.setOnAction(e->deleteStudent());
        displayArea.setFont(font);
        displayArea.setEditable(false);
        displayArea.setWrapText(true);

        gridPane.add(new Lable("ID:"),0,0);
        gridPane.add(idField,1,0);
        gridPane.add(new Lable("Name:"),0,1);
        gridPane.add(nameField,1,1);
        gridPane.add(new Lable("Age:"),0,2);
        gridPane.add(ageField,1,2);
        gridPane.add(new Lable("Course:"),0,3);
        gridPane.add(courseField,1,3);

        gridPane.add(createButton,0,4);
        gridPane.add(readButton,1,4);
        gridPane.add(updateButton,0,5);
        gridPane.add(deleteButton,1,5);
        GridPane.setMargin(createButton,new Insets(5,5,5,5));
        GridPane.setMargin(readButton,new Insets(5,5,5,5));
        GridPane.setMargin(updateButton,new Insets(5,5,5,5));
        GridPane.setMargin(deleteButton,new Insets(5,5,5,5));

        gridPane.add(displayArea,0,6,2,1);

        Scene scene=new Scene(gridPane,400,500);
        primaryStage.setTitle("Student Management");
        primaryStage.setScence(scene);
        primaryStage.show();
    }
    public void connectToDatabase(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("Database connection successful");
        }
        catch(ClassNotFoundException e){
            System.err.println("MySQL JDBC Driver not found");
            e.printStackTrace();
        }catch(SQLException e){
            System.err.println("Database connection failed");
            e.printStackTrace();
        }
}
private void createStudent(){
    String name=namefield.getText();
    int age=Interger.parseInt(ageField.getText());
    String course=coursefield.gettext();
    String sql="INSERT INTO students(name,age,course)VALUES(?,?,?)";
    try(PreparedStatement pstmt=connection/prepareStatement(sql)){
        pstmt.setString(1,name);
        pstmt.setInt(2,age);
        pstmt.setString(3,course);
        pstmt.executeUpdate();
        displayArea.setText("Student created successfully.");
    }
    catch(SQLException e){
        e.printStackTrace();
    }
}
private void readStudents(){
    String sql="SELECT*FROM students";
    try(Statement stmt=connection.createstatement();
    ResultSet rs=stmt.executeQuery(sql)){
        stringBuilder sb=new StringBuilder();
        while(rs.nexxt()){
            sb.append("ID:").append(rs.getInt("id")).append(",Name:").append(rs.getString("name")).appent(",Age:").append(rs.getInt("age")).append(",Course:").append(rs.getString("course")).append("\n");
        }
        displayArea.setText(sb.toString());
    }catch(SQLException e){
        e.printStackTrace();
    }
}
private void updateStudent(){
    int id=Interger.parseInt(idfield.getText());
    String name=namefield.getText();
    int age=Integer.parseInt(ageField.getText());
    String course=courseField.getText();
    String sql="UPDATE students SET name=?,age=?,course=?WHERE id=?";
    try(PreparedStatement pstmt=connection.prepareStatement(sql)){
        pstmt.setString(1,name);
        pstmt.setInt(2,age);
        pstmt.setString(3,course);
        pstmt.setInt(4,id);
        pstmt.executeUpdate();
        displayArea.setText("Student created successfully.");
    }catch(SQLException e){
        e.printStackTrace();
    }
}
private void DeleteStudent(){
    int id=Integer.parseInt(idField.getText());
    String sql="DELETE FORM students WHERE id=?";
    try(PreparedStatement pstmt=connection.prepareStatement(sql)){
        pstmt.setInt(1,id);
        pstmt.executeUpdate();
        displayArea.setText("Student created successfully.");
    }catch(SQLException e){
        e.printStackTrace();
    }
}
@Override 
public void stop()throws Exception{
    if(connection!=null && !connection.isClosed()){
        connection.close();
    }
    super.stop();
}
}
