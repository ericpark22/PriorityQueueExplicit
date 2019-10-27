/*
    This program implements a priority queue heap-ordered tree using only arrays
*/
import java.util.Arrays;

public class MinMaxPriorityQueue<Key extends Comparable<Key>>
{
    Item[] minHeap = new Item[10];
    Item[] maxHeap = new Item[10];
    int sizeMax = 0; // size of maxHeap
    int sizeMin = 0; //  size of minHeap

    public void insert(Key key)
    {
        Item pqItem = new Item();
        Item pqItem2 = new Item();
        pqItem.key = key;
        pqItem2.key = key;


        if(maxHeap[1] == null) // nothing in priority queue
        {
            maxHeap[1] = pqItem;
            maxHeap[1].maxIndex = 1;
            sizeMax++;

        }

        else
        {
            if(sizeMax+1 > maxHeap.length - 1)
                resizeMax();

            for(int i = 1; i < sizeMax; i++)
            {
                if(maxHeap[i] == null)
                {
                    maxHeap[i] = pqItem;
                    pqItem.maxIndex = i;
                    swimMax(pqItem);
                    return;
                }
            }

            maxHeap[sizeMax + 1] = pqItem; // add item to array
            pqItem.maxIndex = sizeMax + 1; // assign index
            sizeMax++;
            swimMax(pqItem); // correct heap order
        }

        // minHeap Array

        if(minHeap[1] == null) // nothing in priority queue
        {
            minHeap[1] = pqItem2;
            minHeap[1].minIndex = 1;
            sizeMin++;

        }

        else
        {
            if(sizeMin+1 > minHeap.length - 1)
                resizeMin();

            for(int i = 1; i < sizeMin; i++)
            {
                if(minHeap[i] == null)
                {
                    minHeap[i] = pqItem2;
                    pqItem2.minIndex = i;
                    swimMin(pqItem);
                    return;
                }
            }

            minHeap[sizeMin + 1] = pqItem2; // add item to array
            pqItem2.minIndex = sizeMin + 1; // assign index
            sizeMin++;
            swimMin(pqItem2); // correct heap order
        }
    }

    public void swimMax(Item item)
    {
        if(item.maxIndex - 1 == 0) // if at the root
            return;

        int position = item.maxIndex;
        int parentIndex = position/2;


        if((maxHeap[parentIndex].key).compareTo(item.key) < 0) // if parent is less than current
        {
            swapMax(maxHeap[parentIndex], item); // switch
            swimMax(maxHeap[parentIndex]); // continue checking heap order
        }
    }

    public void swimMin(Item item)
    {
        if(item.minIndex - 1 == 0) // if at the root
            return;

        int position = item.minIndex;
        int parentIndex = position/2;


        if((minHeap[parentIndex].key).compareTo(item.key) > 0) // if parent is greater than current
        {
            swapMin(minHeap[parentIndex], item); // switch
            swimMin(minHeap[parentIndex]); // continue checking heap order
        }
    }

    public void swapMax(Item a, Item b) // swaps 2 items in array
    {
        int aPosition = a.maxIndex;
        int bPosition = b.maxIndex;

        Item temp = maxHeap[bPosition];
        Item temp2 = maxHeap[aPosition];

        maxHeap[aPosition] = temp;
        maxHeap[bPosition] = temp2;

        maxHeap[aPosition].maxIndex = aPosition;
        maxHeap[bPosition].maxIndex = bPosition;
    }

    public void swapMin(Item a, Item b) // swaps 2 items in array
    {
        int aPosition = a.minIndex;
        int bPosition = b.minIndex;

        Item temp = minHeap[bPosition];
        Item temp2 = minHeap[aPosition];

        minHeap[aPosition] = temp;
        minHeap[bPosition] = temp2;

        minHeap[aPosition].minIndex = aPosition;
        minHeap[bPosition].minIndex = bPosition;
    }

    public void sinkMax(Item item)
    {
        int position = item.maxIndex;

        if(position*2 + 1 > sizeMax) // if out of bounds array
            return;

        if(maxHeap[position*2] != null)
        {
            if ((maxHeap[position * 2].key).compareTo(item.key) > 0) // if left child is greater than current
            {
                swapMax(maxHeap[position * 2], item); // switch
                sinkMax(maxHeap[position]); // continue checking heap order
            }
        }

        if(maxHeap[position*2 +1] != null)
        {
            if ((maxHeap[position * 2 + 1].key).compareTo(item.key) > 0)// if right child is greater than current
            {
                swapMax(maxHeap[position * 2 + 1], item);
                sinkMax(maxHeap[position]);
            }
        }

        else
            return;
    }

    public void sinkMin(Item item)
    {
        int position = item.minIndex;

        if(position*2 + 1 > sizeMin) // if out of bounds array
            return;

        if(minHeap[position*2] != null)
        {
            if((minHeap[position * 2].key).compareTo(item.key) < 0) // if left child is less than current
            {
                swapMin(minHeap[position * 2], item); // switch
                sinkMin(minHeap[position]); // continue checking heap order
            }
        }

        if(minHeap[position*2 + 1] != null)
        {
            if((minHeap[position * 2 + 1].key).compareTo(item.key) < 0)// if right child is less than current
            {
                swapMin(minHeap[position * 2 + 1], item);
                sinkMin(minHeap[position]);
            }
        }

        else
            return;
    }
    public Comparable<Key> deleteMax()
    {
        Item max = maxHeap[1]; // max is root
        swapMax(maxHeap[1], maxHeap[sizeMax]); // switch recently added with max
        maxHeap[sizeMax] = null; // delete max
        sizeMax--; // resize array

        sinkMax(maxHeap[1]);

        for(int i = 1; i <= sizeMin; i++)
        {
            if(minHeap[i].key.compareTo(max.key) == 0)
            {
                if(i == sizeMin)
                    sizeMin--;

                minHeap[i] = null;
            }
        }

        if(minHeap[sizeMin] == null)
            sizeMin--;
        if(maxHeap[sizeMax] == null)
            sizeMax--;

        return max.key;
    }

    public Comparable<Key> deleteMin()
    {
        Item min = minHeap[1]; // min is root

        swapMin(minHeap[1], minHeap[sizeMin]); // switch recently added with min

        minHeap[sizeMin] = null; // delete min
        sizeMin--; // resize array

        sinkMin(minHeap[1]);

        for(int i = 1; i <= sizeMax; i++)
        {
            if(maxHeap[i].key.compareTo(min.key) == 0)
            {
                if(i == sizeMax)
                    sizeMax--;

                maxHeap[i] = null;
            }
        }

        if(minHeap[sizeMin] == null)
            sizeMin--;
        if(maxHeap[sizeMax] == null)
            sizeMax--;

        return min.key;
    }

    public Comparable<Key> findMax()
    {
        return maxHeap[1].key;
    }

    public Comparable<Key> findMin()
    {
        return minHeap[1].key;
    }


    public void resizeMax()
    {
        maxHeap = Arrays.copyOf(maxHeap, maxHeap.length*2);
    }
    public void resizeMin()
    {
        minHeap = Arrays.copyOf(minHeap, minHeap.length*2);
    }
}
