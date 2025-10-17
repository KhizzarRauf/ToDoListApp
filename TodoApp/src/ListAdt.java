/**
 * Interface for List - ordered list of items
 * @param <E> - type of elements in the list
 */
public interface ListAdt<E> {
    /**
     * Method to add new item to the list
     * @param item - new item to be added
     */
    void addItem(E item);

    /**
     * Method to remove an item from the list
     * @param index - index of the item to be removed
     * @return - the removed item
     * @throws IndexOutOfBoundsException - if index out of the list size
     */
    E removeItem(int index) throws IndexOutOfBoundsException;

    /**
     * Method to retrieve an item from List on given Index
     * @param index - index of element
     * @return Item on that index in list
     * @throws IndexOutOfBoundsException - if index out of the list size
     */
    E getItem(int index) throws IndexOutOfBoundsException;

    /**
     * Method to set an Item at a given index
     * @param index - index where new elements needs to be added
     * @param item - the item to be added
     * @return - old item that would be overwritten
     * @throws IndexOutOfBoundsException - if index out of the list size
     */
    E setItem(int index, E item) throws IndexOutOfBoundsException;

    /**
     * Method to return the size of the list
     * @return size of the list
     */
    int size();

    /**
     * Method to return if the list empty
     * @return true if list is empty else false
     */
    boolean isEmpty();

    /**
     * Method to check if item is present in the list
     * @param item - item that needs to be check in the list or not
     * @return - return true if the element exists
     */
    boolean contains(E item);
}
