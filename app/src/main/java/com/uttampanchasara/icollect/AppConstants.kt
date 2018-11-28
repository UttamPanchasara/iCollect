package com.uttampanchasara.icollect

/**
 *
 * @author <a href="https://github.com/UttamPanchasara">Uttam Panchasara</a>
 * @since 11/15/2018
 */
object AppConstants {
    internal val APP_DB_NAME = "base_project.db"


    internal val HTML_HEADER = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "<head>\n" +
            "<style>\n" +
            "table {\n" +
            "    font-family: arial, sans-serif;\n" +
            "    border-collapse: collapse;\n" +
            "    width: 100%;\n" +
            "}\n" +
            "\n" +
            "td, th {\n" +
            "    border: 1px solid #dddddd;\n" +
            "    text-align: left;\n" +
            "    padding: 8px;\n" +
            "}\n" +
            "\n" +
            "tr:nth-child(even) {\n" +
            "    background-color: #dddddd;\n" +
            "}\n" +
            "</style>\n" +
            "</head>\n" +
            "<body>\n" +
            "\n" +
            "<h2>DOCUMENT_TITLE</h2>\n" +
            "\n" +
            "<table>\n" +
            "  <tr>\n" +
            "  \t<th>Time</th>\n" +
            "    <th>Name</th>\n" +
            "    <th>Code</th>\n" +
            "    <th>Number</th>\n" +
            "  </tr>\n"

    internal val HTML_FOOTER = "</table>\n" +
            "</body>\n" +
            "</html>"
}