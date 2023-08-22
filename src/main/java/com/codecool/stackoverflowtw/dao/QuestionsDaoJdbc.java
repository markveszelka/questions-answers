package com.codecool.stackoverflowtw.dao;

import com.codecool.stackoverflowtw.dao.model.Question;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class QuestionsDaoJdbc implements QuestionsDAO {

    public Connection getConnection() {
        String dbName = "stackoverflow_db";
        String userName = "postgres";
        String password = "333333";
        String url = "jdbc:postgresql://localhost:5432/" + dbName;

        try {
            return DriverManager.getConnection(url, userName, password);
        } catch (SQLException ex) {
            System.err.println("Could not create database connection.");
            throw new RuntimeException(ex);
        }
    }

    @Override
    public void sayHi() {
        System.out.println("Hi DAO!");
    }

    @Override
    public void add(String title, String description) {

    }

    @Override
    public Question get(int id) {
        String sql = "SELECT id, title, description, date_created FROM question WHERE id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);

            try (ResultSet rs = stmt.executeQuery()){
                if (rs.next()){

                    Question question = new Question(
                            rs.getInt("id"),
                            rs.getString("title"),
                            rs.getString("description"),
                            rs.getTimestamp("date_created").toLocalDateTime());

                    System.out.println(
                            rs.getInt("id") + "\t" +
                                    rs.getString("title") + "\t" +
                                    rs.getString("description") + "\t" +
                                    rs.getTimestamp("date_created"));

                    return question;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public List<Question> getAll() {
        String sql = "SELECT id, title, description, date_created FROM question";
        List<Question> questions = new ArrayList<>();

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(rs.getInt("id") + "\t" +
                        rs.getString("title") + "\t" +
                        rs.getString("description") + "\t" +
                        rs.getTimestamp("date_created"));
                questions.add(new Question(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getTimestamp("date_created").toLocalDateTime()));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return questions;
    }

    @Override
    public void update(int id, String title, String description) {


    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void deleteAll() {

    }
}
