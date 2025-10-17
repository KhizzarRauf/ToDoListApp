/**
 * Array Implementation of List
 * @param <E>
 */
public class ArrayList<E> implements ListAdt<E> {

    /**
     * Array of items where list items are stored
     */
    private E[] items;

    /**
     * size of the list that increases or decrease on addition or removal items
     */
    private int size;

    /**
     * Default initial size of list if no size is given
     */
    private static int DEFAULT_SIZE = 2;

    /**
     * Factor at which the list grows
     */
    private static int INCREASE_FACTOR = 2;

    /**
     * Default constructor of list to create a list of Default size
     */
    public ArrayList() {
        this(DEFAULT_SIZE);
    }

    /**
     * Constructor to initialize the List with given capacity
     * @param initialCapacity - initial desired capacity of list
     */
    public ArrayList(int initialCapacity) {
        if(initialCapacity > 0) {
            this.items = (E[]) new Object[initialCapacity];
        } else {
            throw new IllegalArgumentException("Invalid Initial Capacity : " + initialCapacity);
        }

    }

    /**
     * Metgod to add item in list
     * @param item - new item to be added
     */
    @Override
    public void addItem(E item) {
        // if list is full the increase the size of list
        if(this.size == items.length)
            increaseSize();
        items[size] = item;
        size++;

    }

    /**
     * Method to remove an item at given index
     * @param index - index of the item to be removed
     * @return removed item
     * @throws IndexOutOfBoundsException - if invalid index
     */
    @Override
    public E removeItem(int index) throws IndexOutOfBoundsException {
        if(!isValidIndex(index))
            throw new IndexOutOfBoundsException();
        E removedElement = items[index];
        for(int i = index+1; i<size; i++) {
            items[i-1] = items[i];
        }
        size--;
        return removedElement;
    }

    /**
     * Method to get item by given index
     * @param index - index of item
     * @return item at given index
     * @throws IndexOutOfBoundsException - if invalid index
     */
    @Override
    public E getItem(int index) throws IndexOutOfBoundsException {
        if(!isValidIndex(index))
            throw new IndexOutOfBoundsException();
        return items[index];
    }

    /**
     * Method to set item at given index
     * @param index - index where new elements needs to be added
     * @param item  - the item to be added
     * @return old item present at the given index
     * @throws IndexOutOfBoundsException - if invalid index
     */
    @Override
    public E setItem(int index, E item) throws IndexOutOfBoundsException {
        if(!isValidIndex(index))
            throw new IndexOutOfBoundsException();
        E oldVal = items[index];
        items[index] = item;
        return oldVal;
    }

    /**
     * Method to return the size of the list
     * @return size of the list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Method to check if list is empty
     * @return true if empty else false
     */
    @Override
    public boolean isEmpty() {
        return size==0;
    }

    /**
     * Method to check if item is present in the list
     * @param item - item that needs to be check in the list or not
     * @return true if exist else false
     */
    @Override
    public boolean contains(E item) {
        return indexOfValue(item) >=0;
    }

    /**
     * Method to find the index value of the given item
     * @param item - item for which we need to find the index
     * @return index of the given item
     */
    private int indexOfValue(E item) {
        if (item == null) {
            for (int i = 0; i < size; i++) {
                if (items[i] == null) {
                    return i;
                }
            }
        } else {
            for (int i = 0; i < size; i++) {
                if (item.equals(items[i])) {
                    return i;
                }
            }
        }
        return -1;
    }

    /**
     * Method to increase the size of the list by the increase factor
     */
    private void increaseSize() {
        int newSize = size + (size*INCREASE_FACTOR);
        E[] tempArr = (E[]) new Object[newSize];
        for(int i = 0; i<size; i++){
            tempArr[i] = items[i];
        }
        items = tempArr;
    }

    /**
     * Method to check if the index is valid that is within bounds
     * @param index - value needs to be checked if valid
     * @return true if valid index else false
     */
    private boolean isValidIndex(int index) {
        return index >=0 && index < size;
    }
}
