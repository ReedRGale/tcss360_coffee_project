/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package objects;
import java.sql.Date;

/**
 *
 * @author Jared Lowery
 */
public class Review 
{
    private int reviewid;
    private int foodrank;
    private int expenserank;
    private int coffeerank;
    private Date dateadded;
    private int owner;
    private int shop;
    private String comment;

    public int getReviewid() {
        return reviewid;
    }

    public void setReviewid(int reviewid) {
        this.reviewid = reviewid;
    }

    public int getFoodrank() {
        return foodrank;
    }

    public void setFoodrank(int foodrank) {
        this.foodrank = foodrank;
    }

    public int getExpenserank() {
        return expenserank;
    }

    public void setExpenserank(int expenserank) {
        this.expenserank = expenserank;
    }

    public int getCoffeerank() {
        return coffeerank;
    }

    public void setCoffeerank(int coffeerank) {
        this.coffeerank = coffeerank;
    }

    public Date getDateadded() {
        return dateadded;
    }

    public void setDateadded(Date dateadded) {
        this.dateadded = dateadded;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }
    
    public int getShop() {
        return shop;
    }

    public void setShop(int shop) {
        this.shop = shop;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
    
    
}
