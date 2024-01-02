def is_balanced_c_code_statements(statements):
    stack = []
    in_block_comment = False

    for line in statements:
        i = 0
        while i < len(line):
            # Handle block comment start
            if not in_block_comment and i < len(line) - 1 and line[i : i + 2] == "/*":
                stack.append("/*")
                in_block_comment = True
                i += 1
            # Handle block comment end
            elif in_block_comment and i < len(line) - 1 and line[i : i + 2] == "*/":
                if not stack or stack[-1] != "/*":
                    return False
                stack.pop()
                in_block_comment = False
                i += 1
            # Handle single-line comments
            elif not in_block_comment and i < len(line) - 1 and line[i : i + 2] == "//":
                break  # Ignore rest of the line
            # Handle symbols if not in a block comment
            elif not in_block_comment:
                if line[i] in "[{":
                    stack.append(line[i])
                elif line[i] in "]}":
                    if not stack:
                        return False
                    if (line[i] == "]" and stack[-1] != "[") or (
                        line[i] == "}" and stack[-1] != "{"
                    ):
                        return False
                    stack.pop()
            i += 1

        # If a block comment is not closed by the end of a line, it continues to the next line
        if in_block_comment and stack[-1] != "/*":
            return False

    return len(stack) == 0


def is_balanced_c_plus_code_statements(statements):
    stack = []
    in_block_comment = False

    for line in statements:
        i = 0
        while i < len(line):
            # Handle single-line comments
            if not in_block_comment and i < len(line) - 1 and line[i : i + 2] == "//":
                break  # Ignore rest of the line
            # Handle symbols if not in a block comment
            elif not in_block_comment:
                if line[i] in "([{":
                    stack.append(line[i])
                elif line[i] in ")]}":
                    if not stack:
                        return False
                    if (
                        (line[i] == ")" and stack[-1] != "(")
                        or (line[i] == "]" and stack[-1] != "[")
                        or (line[i] == "}" and stack[-1] != "{")
                    ):
                        return False
                    stack.pop()
            i += 1

        # If a block comment is not closed by the end of a line, it continues to the next line
        if in_block_comment and stack[-1] != "/*":
            return False

    return len(stack) == 0


def is_balanced_c_code_statements_with_errors(statements):
    stack = []
    in_block_comment = False
    line_number = 0

    for line in statements:
        line_number += 1
        i = 0
        while i < len(line):
            # Handle block comment start
            if not in_block_comment and i < len(line) - 1 and line[i : i + 2] == "/*":
                stack.append(("/*", line_number))
                in_block_comment = True
                i += 1
            # Handle block comment end
            elif in_block_comment and i < len(line) - 1 and line[i : i + 2] == "*/":
                if not stack or stack[-1][0] != "/*":
                    return f"Unmatched block comment end on line {line_number}"
                stack.pop()
                in_block_comment = False
                i += 1
            # Handle single-line comments
            elif not in_block_comment and i < len(line) - 1 and line[i : i + 2] == "//":
                break  # Ignore rest of the line
            # Handle symbols if not in a block comment
            elif not in_block_comment:
                if line[i] in "[{":
                    stack.append((line[i], line_number))
                elif line[i] in "]}":
                    if not stack:
                        return f"Unmatched closing {line[i]} on line {line_number}"
                    opening_symbol, opening_line = stack[-1]
                    if (line[i] == "]" and opening_symbol != "[") or (
                        line[i] == "}" and opening_symbol != "{"
                    ):
                        return f"Unmatched closing {line[i]} on line {line_number}, expected {opening_symbol} from line {opening_line}"
                    stack.pop()
            i += 1

    # Check if there is an unclosed block comment
    if in_block_comment:
        _, opening_line = stack[-1]
        return f"Unclosed block comment starting from line {opening_line}"

    # Check for any other unclosed symbols
    if stack:
        opening_symbol, opening_line = stack[-1]
        return f"Unclosed {opening_symbol} starting from line {opening_line}"

    return "No errors found"


def is_balanced_c_plus_code_statements_with_errors(statements):
    stack = []
    in_block_comment = False
    line_number = 0

    for line in statements:
        line_number += 1
        i = 0
        while i < len(line):
            # Handle block comment start
            if not in_block_comment and i < len(line) - 1 and line[i : i + 2] == "//":
                break  # Ignore rest of the line
            # Handle symbols if not in a block comment
            elif not in_block_comment:
                if line[i] in "([{":
                    stack.append((line[i], line_number))
                elif line[i] in ")]}":
                    if not stack:
                        return f"Unmatched closing {line[i]} on line {line_number}"
                    opening_symbol, opening_line = stack[-1]
                    if (
                        (line[i] == ")" and opening_symbol != "(")
                        or (line[i] == "]" and opening_symbol != "[")
                        or (line[i] == "}" and opening_symbol != "{")
                    ):
                        return f"Unmatched closing {line[i]} on line {line_number}, expected {opening_symbol} from line {opening_line}"
                    stack.pop()
            i += 1

    # Check if there is an unclosed block comment
    if in_block_comment:
        _, opening_line = stack[-1]
        return f"Unclosed block comment starting from line {opening_line}"

    # Check for any other unclosed symbols
    if stack:
        opening_symbol, opening_line = stack[-1]
        return f"Unclosed {opening_symbol} starting from line {opening_line}"

    return "No errors found"


def readfile(filename):
    with open(filename, "r") as f:
        return f.read().splitlines()


# Read the test cases from the file

input = readfile("test.c")

# print(is_balanced_c_code_statements(input))

# print(is_balanced_c_code_statements(input))
print(is_balanced_c_code_statements_with_errors(input))
# print(is_balanced_c_plus_code_statements(input))
# print(is_balanced_c_code_statements_with_errors(input))


# Example test cases with potential errors
# test_cases_with_errors = [
#     ["int main() {", "    /* This is a comment", "    int array[5]; // Single line comment", "    return 0;"],
#     ["/* comment with no end"],
#     ["int x = 0; // testing )", "int y = (x * 2);"],
#     ["int main() {", "    /* Unclosed comment"]
# ]

# # Run the test cases with error reporting
# error_results = [is_balanced_c_code_statements_with_errors(case) for case in test_cases_with_errors]
# error_results
