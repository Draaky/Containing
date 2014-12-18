/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package containing;

/**
 *
 * @author Freerk
 */
public class StorageRowNumber {
    
    public int returnRowNumber(String transportType)
    {
        if(transportType.toLowerCase().equals("zeeschip"))
        {
            return 0;
        }
        else if(transportType.toLowerCase().equals("binnenschip"))
        {
            return 20;
        }
        else if(transportType.toLowerCase().equals("vrachtauto"))
        {
            return 40;
        }
        else if(transportType.toLowerCase().equals("trein"))
        {
            return 60;
        }
        else
        {
            return 999;
        }
    }
    
}
