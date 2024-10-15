package uno.soft.service.fast_and_correct;

/**
 * This class represents an element in a line with its associated column number.
 * It helps track which element belongs to which column, making it easier to organize the lines later.
 *
 * @param lineElement
 * @param columnNum
 */
public record NewLineElement(String lineElement, int columnNum) {
}
