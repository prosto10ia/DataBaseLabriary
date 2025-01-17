package org.example.libriary;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.sql.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Pattern;


import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.kernel.geom.PageSize;
import java.sql.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.scene.control.TextField;






public class HelloController {
    boolean stud_librarian; // 0 - stud || 1 - librarian
    boolean chb_book = false; // 0 - chb || 1 - book
    DatabaseConnection base = new DatabaseConnection();


    // Корневой элемент
    @FXML
    private AnchorPane rootPane;

    // Главная панель
    @FXML
    private Pane main_pane;

    // Панели
    @FXML
    private Pane header_pane;
    @FXML
    private Pane header_search_pane;
    @FXML
    private Pane table_pane;
    @FXML
    private Pane reg_pane;
    @FXML
    private Pane librarian_ponel;
    @FXML
    private Pane librarian_ponel__chb_ponel;
    @FXML
    private Pane search_book_pane;
    @FXML
    private Pane search_chb_pane;

    // Кнопки
    @FXML
    private Button back_butt;
    @FXML
    private Button book_switch_butt;
    @FXML
    private Button chb_switch_butt;
    @FXML
    private Button librarian_ponel_repport_butt;
    @FXML
    private Button librarian_ponel_add_butt;
    @FXML
    private Button search_butt;
    @FXML
    private Button reg_stud_butt;
    @FXML
    private Button reg_librarian_butt;

    // Поля поиска
    @FXML
    private TextField book_id_field;

    @FXML
    private TextField report_month_field;

    @FXML
    private TextField book_name_field;

    @FXML
    private TextField book_genre_field;

    @FXML
    private TextField book_awtor_field;

    @FXML
    private TextField chb_id_field;

    @FXML
    private TextField chb_name_field;

    @FXML
    private TextField add_book_size_field;

    @FXML
    private TextField add_book_place_field;

    @FXML
    private TextField chp_book_number_field;


    // Инициализация контроллера
    @FXML
    public void initialize() {
        System.out.println("Controller initialized with root: " + rootPane);

        loadFullTableChB();

        search_book_pane.setVisible(false);
        librarian_ponel_add_butt.setVisible(false);
        add_book_size_field.setVisible(false);
        add_book_place_field.setVisible(false);
    }

    // Методы-входа событий

    @FXML
    private void back_butt_pressed() {
        System.out.println("Back button pressed");
        reg_pane.setVisible(true);
    }

    @FXML
    private void reg_stud_butt_pressed() {
        System.out.println("Register as student button pressed");
        stud_librarian = false;
        reg_pane.setVisible(false);
        table_pane.setLayoutX(120);
        librarian_ponel.setVisible(false);
    }

    @FXML
    private void reg_librarian_butt_pressed() {
        System.out.println("Register as librarian button pressed");
        stud_librarian = true;
        reg_pane.setVisible(false);
        table_pane.setLayoutX(40);
        librarian_ponel.setVisible(true);

    }

    // Методы-смены данных

    @FXML
    private void book_switch_butt_pressed() {
        System.out.println("Book switch button pressed");

        loadFullTableChB();

        chb_book = false;
        search_book_pane.setVisible(false);
        search_chb_pane.setVisible(true);
        book_switch_butt.setVisible(false);
        chb_switch_butt.setVisible(true);
        add_book_size_field.setVisible(false);
        add_book_place_field.setVisible(false);
        librarian_ponel_add_butt.setVisible(false);
        librarian_ponel__chb_ponel.setVisible(true);


    }

    @FXML
    private void chb_switch_butt_pressed() {
        System.out.println("CHB switch button pressed");

        loadFullTableBook();

        chb_book = true;
        search_book_pane.setVisible(true);
        search_chb_pane.setVisible(false);
        book_switch_butt.setVisible(true);
        chb_switch_butt.setVisible(false);
        add_book_size_field.setVisible(true);
        add_book_place_field.setVisible(true);
        librarian_ponel_add_butt.setVisible(true);
        librarian_ponel__chb_ponel.setVisible(false);
    }


    // Методы действий пользователя

    @FXML
    private void search_butt_pressed() {
        if (chb_book) {
            String id = getBookId();
            String name = getBookName();
            String genre = getBookGenre();
            String author = getBookAuthor();

            // Проверка ID книги
            if (id != null && !id.isBlank()) {
                if (!BookID.check(id)) {
                    showError("Ошибка ввода ID");
                    return;
                }
            }

            // Проверка названия книги
            if (name != null && !name.isBlank()) {
                if (!Name.check(name)) {
                    showError("Ошибка ввода названия книги");
                    return;
                }
            }

            // Проверка ФИО автора
            if (author != null && !author.isBlank()) {
                if (!FIO.check(author)) {
                    showError("Ошибка ввода ФИО");
                    return;
                }
            }

            // Проверка жанра
            if (genre != null && !genre.isBlank()) {
                if (!Genre.check(genre)) {
                    showError("Ошибка ввода жанра");
                    return;
                }
            }

            // Если все проверки прошли успешно, выполняем поиск
            loadSearchTableBook(id, name, author, genre);
        } else {
            String studentId = getStudentId();
            String studentName = getStudentName();

            // Проверка ID студента
            if (studentId != null && !studentId.isBlank()) {
                if (!Grade.check(studentId)) {
                    showError("Ошибка ввода ID студента");
                    return;
                }
            }

            // Проверка имени студента
            if (studentName != null && !studentName.isBlank()) {
                if (!FIO.check(studentName)) {
                    showError("Ошибка ввода имени студента");
                    return;
                }
            }

            // Если все проверки прошли успешно, выполняем поиск
            loadSearchTableChB(studentId, studentName);
        }
    }

    @FXML
    private void chp_ponel_take_butt_pressed() {
        System.out.println("take book pressed");

        try {
            String studentId = getStudentId();
            String bookId = getBookNumber();
            LocalDate takeDate = LocalDate.now();

            if (studentId == null || studentId.isBlank() || !Grade.check(studentId)) {
                showError("Ошибка ввода ID студента");
                return;
            }

            if (bookId == null || bookId.isBlank() || !BookID.check(bookId)) {
                showError("Ошибка ввода ID книги");
                return;
            }

            DatabaseConnection db = new DatabaseConnection();
            if (db.getConnection() == null) {
                showError("Нет соединения с базой данных");
                return;
            }

            // Проверка на последнюю дату возврата
            String checkQuery = """
            SELECT MAX(back_date) AS last_return_date 
            FROM chb 
            WHERE book_id = ?
        """;
            PreparedStatement checkStmt = db.prepareStatement(checkQuery);
            if (checkStmt == null) {
                return;
            }
            checkStmt.setString(1, bookId);
            ResultSet resultSet = checkStmt.executeQuery();

            if (resultSet.next()) {
                Date lastReturnDate = resultSet.getDate("last_return_date");
                if (lastReturnDate != null && lastReturnDate.toLocalDate().isAfter(takeDate)) {
                    showError("Книга не может быть взята, так как она уже находится у другого студента или дата возврата не корректна");
                    return;
                }
            }

            // Проверка, что книга не взята другим студентом
            String currentCheckQuery = "SELECT * FROM chb WHERE book_id = ? AND back_date IS NULL";
            PreparedStatement currentCheckStmt = db.prepareStatement(currentCheckQuery);
            if (currentCheckStmt == null) {
                return;
            }
            currentCheckStmt.setString(1, bookId);
            ResultSet currentResult = currentCheckStmt.executeQuery();

            if (currentResult.next()) {
                showError("Книга уже находится у другого студента");
                return;
            }

            // Проверка, что данный студент не брал книгу и не вернул её
            String studentCheckQuery = "SELECT * FROM chb WHERE stud_id = ? AND book_id = ? AND back_date IS NOT NULL";
            PreparedStatement studentCheckStmt = db.prepareStatement(studentCheckQuery);
            if (studentCheckStmt == null) {
                return;
            }
            studentCheckStmt.setString(1, studentId);
            studentCheckStmt.setString(2, bookId);
            ResultSet studentResult = studentCheckStmt.executeQuery();

            if (studentResult.next()) {
                // Студент уже брал и возвращал книгу — обновляем дату взятия и очищаем дату возврата
                String updateQuery = """
                UPDATE chb 
                SET take_date = ?, back_date = NULL 
                WHERE stud_id = ? AND book_id = ? AND back_date IS NOT NULL
            """;
                PreparedStatement updateStmt = db.prepareStatement(updateQuery);
                if (updateStmt == null) {
                    return;
                }
                updateStmt.setDate(1, java.sql.Date.valueOf(takeDate));
                updateStmt.setString(2, studentId);
                updateStmt.setString(3, bookId);

                int rowsAffected = updateStmt.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Дата взятия книги обновлена");
                    loadFullTableChB();
                } else {
                    showError("Не удалось обновить дату взятия книги");
                }
                return; // Прерываем дальнейшее выполнение метода, так как обновление уже выполнено
            }

            // Добавление записи о взятии книги
            String insertQuery = "INSERT INTO chb (stud_id, book_id, take_date) VALUES (?, ?, ?)";
            PreparedStatement insertStmt = db.prepareStatement(insertQuery);
            if (insertStmt == null) {
                return;
            }
            insertStmt.setString(1, studentId);
            insertStmt.setString(2, bookId);
            insertStmt.setDate(3, java.sql.Date.valueOf(takeDate));

            int rowsAffected = insertStmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Книга успешно взята");
                loadFullTableChB();
            } else {
                showError("Не удалось зафиксировать взятие книги");
            }
        } catch (Exception e) {
            showError("Произошла ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void chp_ponel_back_butt_pressed() {
        System.out.println("return book pressed");

        try {
            String studentId = getStudentId();
            String bookId = getBookNumber();
            LocalDate returnDate = LocalDate.now();

            if (studentId == null || studentId.isBlank() || !Grade.check(studentId)) {
                showError("Ошибка ввода ID студента");
                return;
            }

            if (bookId == null || bookId.isBlank() || !BookID.check(bookId)) {
                showError("Ошибка ввода ID книги");
                return;
            }

            DatabaseConnection db = new DatabaseConnection();
            if (db.getConnection() == null) {
                showError("Нет соединения с базой данных");
                return;
            }

            // Проверка, что студент взял книгу, но ещё не вернул её
            String checkQuery = "SELECT * FROM chb WHERE stud_id = ? AND book_id = ? AND back_date IS NULL";
            PreparedStatement checkStmt = db.prepareStatement(checkQuery);
            if (checkStmt == null) {
                return;
            }
            checkStmt.setString(1, studentId);
            checkStmt.setString(2, bookId);
            ResultSet resultSet = checkStmt.executeQuery();

            if (!resultSet.next()) {
                showError("Студент не взял книгу или уже вернул её");
                return;
            }

            // Обновление записи: установка даты возврата
            String updateQuery = "UPDATE chb SET back_date = ? WHERE stud_id = ? AND book_id = ? AND back_date IS NULL";
            PreparedStatement updateStmt = db.prepareStatement(updateQuery);
            if (updateStmt == null) {
                return;
            }
            updateStmt.setDate(1, java.sql.Date.valueOf(returnDate));
            updateStmt.setString(2, studentId);
            updateStmt.setString(3, bookId);

            int rowsAffected = updateStmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Книга успешно возвращена");
                loadFullTableChB();
            } else {
                showError("Не удалось зафиксировать возврат книги");
            }
        } catch (Exception e) {
            showError("Произошла ошибка: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void librarian_ponel_add_butt_pressed() {
        System.out.println("add book pressed");

        if (chb_book) {
            String id = getBookId();
            String name = getBookName();
            String author = getBookAuthor();
            String size = getBookSize();
            String genre = getBookGenre();
            String shelf = getBookPlace();

            if (id == null || id.isBlank() || !BookID.check(id)) {
                showError("Ошибка ввода ID");
                return;
            }

            if (name == null || name.isBlank() || !Name.check(name)) {
                showError("Ошибка ввода названия книги");
                return;
            }

            if (author == null || author.isBlank() || !FIO.check(author)) {
                showError("Ошибка ввода ФИО");
                return;
            }

            if (size == null || size.isBlank() || !Size.check(size)) {
                showError("Ошибка ввода размера книги");
                return;
            }

            if (genre == null || genre.isBlank() || !Genre.check(genre)) {
                showError("Ошибка ввода жанра");
                return;
            }

            if (shelf == null || shelf.isBlank() || !Shelf.check(shelf)) {
                showError("Ошибка ввода места на полке");
                return;
            }

            addBookToLibrary(id, name, author, size, genre, shelf);
        }
    }

    @FXML
    private void librarian_ponel_repport_butt_pressed() {
        System.out.println("Generate report button pressed");

        // Получаем месяц и год из текстового поля
        String monthInput = report_month_field.getText();
        if (monthInput == null || monthInput.isEmpty()) {
            showError("Пожалуйста, введите месяц и год в формате мм.гггг.");
            return;
        }

        // Проверка формата даты (мм.гггг)
        if (!Pattern.matches("\\d{2}\\.\\d{4}", monthInput)) {
            showError("Неверный формат даты. Используйте формат мм.гггг.");
            return;
        }

        try {
            String fullDate = "01." + monthInput;

            // Преобразуем строку в LocalDate
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
            LocalDate inputDate = LocalDate.parse(fullDate, formatter);

            // Получаем первый день месяца
            LocalDate startDate = inputDate.withDayOfMonth(1);

            // Получаем последний день месяца
            LocalDate endDate = inputDate.withDayOfMonth(inputDate.lengthOfMonth());

            // Подключение к базе данных
            DatabaseConnection db = new DatabaseConnection();
            if (db.getConnection() == null) {
                showError("Нет соединения с базой данных");
                return;
            }

            // Запрос к базе данных для получения данных по книгам, взятым в указанный период
            String query = """
            SELECT stud_id, book_id, take_date, back_date 
            FROM chb 
            WHERE take_date >= ? AND take_date <= ? 
            ORDER BY take_date
        """;
            PreparedStatement stmt = db.prepareStatement(query);
            stmt.setDate(1, java.sql.Date.valueOf(startDate));
            stmt.setDate(2, java.sql.Date.valueOf(endDate));
            ResultSet resultSet = stmt.executeQuery();

            // Проверка на наличие данных
            if (!resultSet.isBeforeFirst()) {
                showError("Нет данных за указанный период.");
                return;
            }

            // Создание PDF документа
            String filePath = "report_" + monthInput + ".pdf";
            PdfWriter writer = new PdfWriter(new FileOutputStream(filePath));
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, PageSize.A4);

            // Заголовок документа
            document.add(new Paragraph("Отчет по книгам с " + startDate + " по " + endDate).setBold().setFontSize(14));

            // Создание таблицы с колонками
            float[] columnWidths = {1, 1, 2, 2};  // Ширина колонок
            Table table = new Table(columnWidths);
            table.addCell(new Cell().add(new Paragraph("ID Студента")));
            table.addCell(new Cell().add(new Paragraph("ID Книги")));
            table.addCell(new Cell().add(new Paragraph("Дата взятия")));
            table.addCell(new Cell().add(new Paragraph("Дата возврата")));

            // Добавление данных в таблицу
            while (resultSet.next()) {
                String studId = resultSet.getString("stud_id");
                String bookId = resultSet.getString("book_id");
                Date takeDate = resultSet.getDate("take_date");
                Date backDate = resultSet.getDate("back_date");

                table.addCell(new Cell().add(new Paragraph(studId)));
                table.addCell(new Cell().add(new Paragraph(bookId)));
                table.addCell(new Cell().add(new Paragraph(takeDate.toString())));
                table.addCell(new Cell().add(new Paragraph(backDate != null ? backDate.toString() : "Не возвращена")));
            }

            document.add(table);
            document.close();

            System.out.println("Отчет успешно сгенерирован: " + filePath);
        } catch (SQLException | FileNotFoundException | DateTimeParseException e) {
            showError("Ошибка при формировании отчета: " + e.getMessage());
            e.printStackTrace();
        }
    }











    // DataBase viewer ===============

    public void loadFullTableBook() {
        try {
            // Очистка содержимого панели
            table_pane.getChildren().clear();

            // Создание TableView
            TableView<Book> tableView = new TableView<>();
            tableView.setPrefSize(850, 550);

            // Создание столбцов
            TableColumn<Book, String> idColumn = new TableColumn<>("ID");
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            idColumn.setPrefWidth(150);

            TableColumn<Book, String> nameColumn = new TableColumn<>("Name");
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            nameColumn.setPrefWidth(200);

            TableColumn<Book, String> authorColumn = new TableColumn<>("Author");
            authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
            authorColumn.setPrefWidth(200);

            TableColumn<Book, String> genreColumn = new TableColumn<>("Genre");
            genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));
            genreColumn.setPrefWidth(150);

            TableColumn<Book, Integer> sizeColumn = new TableColumn<>("Size");
            sizeColumn.setCellValueFactory(new PropertyValueFactory<>("size"));
            sizeColumn.setPrefWidth(48);

            TableColumn<Book, String> shelfColumn = new TableColumn<>("Shelf");
            shelfColumn.setCellValueFactory(new PropertyValueFactory<>("shelf"));
            shelfColumn.setPrefWidth(100);

            // Добавление столбцов в таблицу
            tableView.getColumns().addAll(idColumn, nameColumn, authorColumn, genreColumn, sizeColumn, shelfColumn);

            // Проверка подключения и чтение данных из базы данных
            String query = "SELECT id, name, author, genre, size, shelf FROM book"; // Таблица "book"
            ResultSet resultSet = base.executeQuery(query);

            // Создание списка для хранения данных
            ObservableList<Book> books = FXCollections.observableArrayList();

            while (resultSet.next()) {
                String bookId = resultSet.getString("id");
                String title = resultSet.getString("name");
                String author = resultSet.getString("author");
                String genre = resultSet.getString("genre");
                int size = resultSet.getInt("size");
                String shelf = resultSet.getString("shelf");

                // Добавление данных в список
                books.add(new Book(bookId, title, author, genre, size, shelf));
            }

            // Установка данных в таблицу
            tableView.setItems(books);

            // Добавление TableView на панель
            table_pane.getChildren().add(tableView);

        } catch (Exception e) {
            System.out.println("Error loading table data: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void loadSearchTableBook(String sid, String sname, String sauthor, String sgenre) {
        // Строим базовый запрос
        String query = "SELECT id, name, author, genre, size, shelf FROM book WHERE 1=1";

        // Проверка параметров и добавление условий в запрос
        if (sid != null && !sid.isBlank() && BookID.check(sid)) {
            query += " AND id = ?";
        }
        if (sname != null && !sname.isBlank() && Name.check(sname)) {
            query += " AND name = ?";
        }
        if (sauthor != null && !sauthor.isBlank() && FIO.check(sauthor)) {
            query += " AND author = ?";
        }
        if (sgenre != null && !sgenre.isBlank() && Genre.check(sgenre)) {
            query += " AND genre = ?";
        }

        // Выполнение запроса с PreparedStatement
        try (PreparedStatement preparedStatement = base.getConnection().prepareStatement(query)) {
            int parameterIndex = 1;

            // Установка параметров для PreparedStatement
            if (sid != null && !sid.isBlank() && BookID.check(sid)) {
                preparedStatement.setString(parameterIndex++, sid);
            }
            if (sname != null && !sname.isBlank() && Name.check(sname)) {
                preparedStatement.setString(parameterIndex++, sname);
            }
            if (sauthor != null && !sauthor.isBlank() && FIO.check(sauthor)) {
                preparedStatement.setString(parameterIndex++, sauthor);
            }
            if (sgenre != null && !sgenre.isBlank() && Genre.check(sgenre)) {
                preparedStatement.setString(parameterIndex++, sgenre);
            }

            // Выполнение запроса и получение результата
            ResultSet resultSet = preparedStatement.executeQuery();

            // Создание списка для хранения данных
            ObservableList<Book> books = FXCollections.observableArrayList();

            while (resultSet.next()) {
                String bookId = resultSet.getString("id");
                String title = resultSet.getString("name");
                String author = resultSet.getString("author");
                String genre = resultSet.getString("genre");
                int size = resultSet.getInt("size");
                String shelf = resultSet.getString("shelf");

                // Добавление данных в список
                books.add(new Book(bookId, title, author, genre, size, shelf));
            }

            // Создание TableView и добавление на панель
            TableView<Book> tableView = new TableView<>();
            tableView.setPrefSize(850, 550);

            // Добавление столбцов в таблицу
            tableView.getColumns().add(createBookTableColumn("ID", "id", 150));
            tableView.getColumns().add(createBookTableColumn("Name", "name", 200));
            tableView.getColumns().add(createBookTableColumn("Author", "author", 200));
            tableView.getColumns().add(createBookTableColumn("Genre", "genre", 150));
            tableView.getColumns().add(createBookTableColumn("Size", "size", 48));
            tableView.getColumns().add(createBookTableColumn("Shelf", "shelf", 100));

            // Установка данных в таблицу
            tableView.setItems(books);

            // Очистка и добавление на панель
            table_pane.getChildren().clear();
            table_pane.getChildren().add(tableView);

        } catch (SQLException e) {
            System.out.println("Error loading table data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public void loadFullTableChB() {
        try {
            // Очистка содержимого панели
            table_pane.getChildren().clear();

            // Создание TableView
            TableView<ChB> tableView = new TableView<>();
            tableView.setPrefSize(850, 550);

            // Создание столбцов
            TableColumn<ChB, String> studentIdColumn = new TableColumn<>("ID студента");
            studentIdColumn.setCellValueFactory(new PropertyValueFactory<>("studentId"));
            studentIdColumn.setPrefWidth(78);

            TableColumn<ChB, String> studentNameColumn = new TableColumn<>("Имя студента");
            studentNameColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
            studentNameColumn.setPrefWidth(220);

            TableColumn<ChB, String> bookIdColumn = new TableColumn<>("ID книги");
            bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
            bookIdColumn.setPrefWidth(150);

            TableColumn<ChB, String> bookNameColumn = new TableColumn<>("Название книги");
            bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("bookName"));
            bookNameColumn.setPrefWidth(200);

            TableColumn<ChB, String> takeDateColumn = new TableColumn<>("Дата взятия");
            takeDateColumn.setCellValueFactory(new PropertyValueFactory<>("takeDate"));
            takeDateColumn.setPrefWidth(100);

            TableColumn<ChB, String> backDateColumn = new TableColumn<>("Дата возврата");
            backDateColumn.setCellValueFactory(new PropertyValueFactory<>("backDate"));
            backDateColumn.setPrefWidth(100);

            // Добавление столбцов в таблицу
            tableView.getColumns().addAll(studentIdColumn, studentNameColumn, bookIdColumn, bookNameColumn, takeDateColumn, backDateColumn);

            // Проверка подключения и чтение данных из базы данных
            String query = """
            SELECT chb.stud_id AS student_id, student.name AS student_name,
                   chb.book_id AS book_id, book.name AS book_name,
                   chb.take_date AS take_date, chb.back_date AS back_date
            FROM chb
            JOIN student ON chb.stud_id = student.id
            JOIN book ON chb.book_id = book.id
        """;

            ResultSet resultSet = base.executeQuery(query);

            // Создание списка для хранения данных
            ObservableList<ChB> chBs = FXCollections.observableArrayList();

            while (resultSet.next()) {
                String studentId = resultSet.getString("student_id");
                String studentName = resultSet.getString("student_name");
                String bookId = resultSet.getString("book_id");
                String bookName = resultSet.getString("book_name");
                String takeDate = resultSet.getString("take_date");
                String backDate = resultSet.getString("back_date");

                // Добавление данных в список
                chBs.add(new ChB(studentId, studentName, bookId, bookName, takeDate, backDate));
            }

            // Установка данных в таблицу
            tableView.setItems(chBs);

            // Добавление TableView на панель
            table_pane.getChildren().add(tableView);

        } catch (Exception e) {
            System.out.println("Error loading table data: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public void loadSearchTableChB(String studentId, String studentName) {
        // Строим базовый запрос
        String query = """
    SELECT chb.stud_id AS student_id, student.name AS student_name,
           chb.book_id AS book_id, book.name AS book_name,
           chb.take_date AS take_date, chb.back_date AS back_date
    FROM chb
    JOIN student ON chb.stud_id = student.id
    JOIN book ON chb.book_id = book.id
    WHERE 1=1
    """;

        // Проверка параметров и добавление условий в запрос
        if (studentId != null && !studentId.isBlank() && Grade.check(studentId)) {
            query += " AND chb.stud_id = ?";
        }
        if (studentName != null && !studentName.isBlank() && FIO.check(studentName)) {
            query += " AND student.name LIKE ?";
        }

        // Выполнение запроса с PreparedStatement
        try (PreparedStatement preparedStatement = base.getConnection().prepareStatement(query)) {
            int parameterIndex = 1;

            // Установка параметров для PreparedStatement
            if (studentId != null && !studentId.isBlank() && Grade.check(studentId)) {
                preparedStatement.setString(parameterIndex++, studentId);
            }
            if (studentName != null && !studentName.isBlank() && FIO.check(studentName)) {
                preparedStatement.setString(parameterIndex++, "%" + studentName + "%"); // Используем LIKE для имени
            }

            // Выполнение запроса и получение результата
            ResultSet resultSet = preparedStatement.executeQuery();

            // Создание списка для хранения данных
            ObservableList<ChB> chBs = FXCollections.observableArrayList();

            while (resultSet.next()) {
                String studentIdResult = resultSet.getString("student_id");
                String studentNameResult = resultSet.getString("student_name");
                String bookId = resultSet.getString("book_id");
                String bookName = resultSet.getString("book_name");
                String takeDate = resultSet.getString("take_date");
                String backDate = resultSet.getString("back_date");

                // Добавление данных в список
                chBs.add(new ChB(studentIdResult, studentNameResult, bookId, bookName, takeDate, backDate));
            }

            // Создание TableView и добавление на панель
            TableView<ChB> tableView = new TableView<>();
            tableView.setPrefSize(850, 550);

            // Добавление столбцов в таблицу
            tableView.getColumns().add(createChBTableColumn("ID студента", "studentId", 78));
            tableView.getColumns().add(createChBTableColumn("Имя студента", "studentName", 220));
            tableView.getColumns().add(createChBTableColumn("ID книги", "bookId", 150));
            tableView.getColumns().add(createChBTableColumn("Название книги", "bookName", 200));
            tableView.getColumns().add(createChBTableColumn("Дата взятия", "takeDate", 100));
            tableView.getColumns().add(createChBTableColumn("Дата возврата", "backDate", 100));

            // Установка данных в таблицу
            tableView.setItems(chBs);

            // Очистка и добавление на панель
            table_pane.getChildren().clear();
            table_pane.getChildren().add(tableView);

        } catch (SQLException e) {
            System.out.println("Error loading table data: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void addBookToLibrary(String id, String name, String author, String size, String genre, String shelf) {
        try {
            // Проверяем, существует ли книга с таким ID
            String checkQuery = "SELECT COUNT(*) FROM book WHERE id = ?";
            PreparedStatement checkStatement = base.getConnection().prepareStatement(checkQuery);
            checkStatement.setString(1, id);
            ResultSet rs = checkStatement.executeQuery();

            if (rs.next() && rs.getInt(1) > 0) {
                showError("Книга с таким номером уже существует");
                return;  // Прерываем выполнение, если книга уже есть в базе
            }

            // Если книги с таким ID нет, добавляем новую
            String query = "INSERT INTO book (id, name, author, size, genre, shelf) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = base.getConnection().prepareStatement(query);
            statement.setString(1, id);
            statement.setString(2, name);
            statement.setString(3, author);
            statement.setInt(4, Integer.parseInt(size));
            statement.setString(5, genre);
            statement.setString(6, shelf);
            statement.executeUpdate();
            loadFullTableBook();
            System.out.println("Book added successfully");

        } catch (Exception e) {
            showError("Что-то пошло не по плану, уже чиним");
            e.printStackTrace();
        }
    }











    private void showError(String message) {
        // Создаем и настраиваем Alert для ошибки
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Что-то не так!");
        alert.setHeaderText("Произошла ошибка");
        alert.setContentText(message);

        // Показываем Alert
        alert.showAndWait();
    }

    private TableColumn<Book, String> createBookTableColumn(String title, String property, int width) {
        TableColumn<Book, String> column = new TableColumn<>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        column.setPrefWidth(width);
        return column;
    }
    private TableColumn<ChB, String> createChBTableColumn(String title, String property, int width) {
        TableColumn<ChB, String> column = new TableColumn<>(title);
        column.setCellValueFactory(new PropertyValueFactory<>(property));
        column.setPrefWidth(width);
        return column;
    }


    //  Гетеры   =====

    public String getBookId() {
        return book_id_field.getText();
    }
    public String getBookName() {
        return book_name_field.getText();
    }
    public String getBookGenre() {
        return book_genre_field.getText();
    }
    public String getBookAuthor() {
        return book_awtor_field.getText();
    }
    public String getBookSize() {
        return add_book_size_field.getText();
    }
    public String getBookPlace() {
        return add_book_place_field.getText();
    }
    public String getBookNumber() {
        return chp_book_number_field.getText();
    }

    public String getStudentId() {
        return chb_id_field.getText();
    }
    public String getStudentName() {
        return chb_name_field.getText();
    }

}