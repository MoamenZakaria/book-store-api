INSERT INTO book_type (id, type, created_date, updated_date)
VALUES (1, 'Fiction', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (2, 'Non-Fiction', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (3, 'Biography', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (4, 'History', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (5, 'Science', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (6, 'Fantasy', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (7, 'Romance', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (8, 'Mystery', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (9, 'Horror', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (10, 'Thriller', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO book (id, name, description, author, book_type_id, price, isbn, created_date, updated_date)
VALUES (1, 'To Kill a Mockingbird', 'A novel by Harper Lee', 'Harper Lee', 1, 14.99, '978-3-16-148411-0',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (2, 'The Catcher in the Rye', 'A novel by J.D. Salinger', 'J.D. Salinger', 1, 12.99, '978-3-16-148412-0',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (3, '1984', 'A novel by George Orwell', 'George Orwell', 1, 10.99, '978-3-16-148413-0', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP),
       (4, 'Pride and Prejudice', 'A novel by Jane Austen', 'Jane Austen', 7, 16.99, '978-3-16-148414-0',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (5, 'The Lord of the Rings', 'A novel by J.R.R. Tolkien', 'J.R.R. Tolkien', 6, 19.99, '978-3-16-148415-0',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (6, 'The Hitchhiker''s Guide to the Galaxy', 'A novel by Douglas Adams', 'Douglas Adams', 5, 11.99,
        '978-3-16-148416-0', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (7, 'The Da Vinci Code', 'A novel by Dan Brown', 'Dan Brown', 8, 13.99, '978-3-16-148417-0', CURRENT_TIMESTAMP,
        CURRENT_TIMESTAMP),
       (8, 'The Girl with the Dragon Tattoo', 'A novel by Stieg Larsson', 'Stieg Larsson', 8, 14.99,
        '978-3-16-148418-0', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (9, 'The Hunger Games', 'A novel by Suzanne Collins', 'Suzanne Collins', 6, 12.99, '978-3-16-148419-0',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (10, 'The Road', 'A novel by Cormac McCarthy', 'Cormac McCarthy', 1, 15.99, '978-3-16-148420-0',
        CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);

INSERT INTO promotion (id, code, book_type_id, discount_percent, created_date, updated_date)
VALUES (1, 'SPRING2023', 1, 10.0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (2, 'SUMMER2023', 2, 15.0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (3, 'FALL2023', 3, 20.0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (4, 'WINTER2023', 4, 25.0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
       (5, 'HOLIDAY2023', 5, 30.0, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);
