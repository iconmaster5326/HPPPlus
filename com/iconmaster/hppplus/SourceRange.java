
package com.iconmaster.hppplus;

/**
 *
 * @author iconmaster
 */
public class SourceRange {
    private final int begin;
    private final int end;
    
    public SourceRange(int begin,int end) {
        this.begin = begin;
        this.end = end;
    }
    
    public int getBegin() {
        return begin;
    }
    
    public int getEnd() {
        return end;
    }
    
    @Override
    public String toString() {
        return begin+"~"+end;
    }
    
    public static SourceRange between(SourceRange range1,SourceRange range2) {
        return new SourceRange(range1.begin,range2.end);
    }
}
