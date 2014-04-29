// Copyright (C) 2003 Iowa State University

// This file is part of JML

// JML is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2, or (at your option)
// any later version.

// JML is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with JML; see the file COPYING.  If not, write to
// the Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.

import java.util.*; // Copyright (C) 2003 Iowa State University

// This file is part of JML

// JML is free software; you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation; either version 2, or (at your option)
// any later version.

// JML is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.

// You should have received a copy of the GNU General Public License
// along with JML; see the file COPYING.  If not, write to
// the Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.

import java.awt.Color; 

/** Directed graphs.
 * @author Katie Becker
 * @author Gary T. Leavens
 */
public   class  Digraph {
	

	//@ public model JMLValueSet nodes;
	//@ public model JMLValueSet arcs;

	/*@ public invariant_redundantly nodes != null;
	   @ public invariant (\forall JMLType n; nodes.has(n);
	   @                          n instanceof NodeType);
	   @ public invariant_redundantly arcs != null;
	   @ public invariant (\forall JMLType a; arcs.has(a);
	   @                          a instanceof ArcType); 
	   @ public invariant
	   @      (\forall ArcType a; arcs.has(a);
	   @           nodes.has(a.from) && nodes.has(a.to));
	   @*/
	 
    protected /*@ spec_public @*/ HashSet nodeSet;

	
    //@                           in nodes;
    //@                           maps nodeSet.theSet \into nodes;
    protected /*@ spec_public @*/ HashSet arcSet;

	
    //@                           in arcs;
    //@                           maps arcSet.theSet \into arcs;

    //@ private represents nodes <- JMLValueSet.convertFrom(nodeSet);
    //@ private represents arcs <- arcAbstractValue(arcSet);

    /*@ private invariant_redundantly nodeSet != null;
      @ private invariant (\forall Object e; nodeSet.contains(e); 
      @                       e!=null && e instanceof NodeType);
      @ 
      @ private invariant_redundantly arcSet != null;
      @ private invariant (\forall Object e; arcSet.contains(e); 
      @                       e!=null && e instanceof Arc);
      @*/

	 /*@  public normal_behavior
	   @   assignable nodes, arcs;
	   @   ensures nodes.isEmpty() && arcs.isEmpty();
	   @*/
    public Digraph() {
        nodeSet = new HashSet();
        arcSet = new HashSet();
    }

	

	 /*@  public normal_behavior
	   @   requires_redundantly n != null;
	   @   assignable nodes;
	   @   ensures nodes.equals(\old(nodes.insert(n)));
	   @*/
    public void addNode(NodeType n) {
        nodeSet.add(n);
    }

	

	 /*@  public normal_behavior
	   @   requires_redundantly inFrom != null && inTo != null;
	   @   requires nodes.has(inFrom) && nodes.has(inTo);
	   @   assignable arcs;
	   @   ensures arcs.equals(
	   @            \old(arcs).insert(new ArcType(inFrom, inTo)));
	   @*/
    public void addArc(NodeType inFrom, NodeType inTo) {
        arcSet.add(new Arc(inFrom, inTo));
    }

	

	/*@  public normal_behavior
	  @   assignable \nothing;
	  @   ensures \result == nodes.has(n);
      @ also 
      @  public normal_behavior
      @    ensures \result == nodeSet.contains(n);
      @*/
    public /*@ pure @*/ boolean isNode(NodeType n) {
        return nodeSet.contains(n);
    }

	

	 /*@  public normal_behavior
	   @   requires_redundantly inFrom != null && inTo != null;
	   @   ensures \result == arcs.has(new ArcType(inFrom, inTo));
	   @
	   @*/
    public /*@ pure @*/ boolean isArc(NodeType inFrom, NodeType inTo) {
        return arcSet.contains(new Arc(inFrom, inTo));
    }

	

	 /*@  public normal_behavior
	   @   requires nodes.has(start) && nodes.has(end);
	   @   assignable \nothing;
	   @   ensures \result
	   @           == reachSet(new JMLValueSet(start)).has(end);
	   @*/
    public /*@ pure @*/ boolean isAPath(NodeType start, NodeType end) {
        return reachSet(start).contains(end);
    }

	

    protected /*@ pure @*/ HashSet reachSet(NodeType start) {
        HashSet disc = new HashSet(); // discovered vertices
        Iterator arcs = arcSet.iterator();
        while (arcs.hasNext()) {
            Arc next = (Arc) arcs.next();
            if (next.getSource() == start && !disc.contains(next.getTarget())) {
                disc.add(next.getTarget());
                reachSet(next.getTarget());
            }
        }

        return disc;
    }

	
  
    /*@ private pure model JMLValueSet arcAbstractValue(HashSet arcs) {
      @   JMLValueSet ret = new JMLValueSet();
      @   Iterator arcIter = arcs.iterator();
      @   while (arcIter.hasNext()) {
      @     Arc a = (Arc) arcIter.next();
      @     NodeType from = a.getSource();
      @     NodeType to = a.getTarget();
      @     ret = ret.insert(new ArcType(from, to));
      @   }
      @   return ret;
      @ }
      @*/

    public String toString() {
        return "[arcs: " + arcSet.toString()
               + "\n nodes: " + nodeSet.toString() + "]";
    }

	

	 /*@  public normal_behavior
	   @   assignable \nothing;
	   @   ensures \result <==>
	   @              !(\exists ArcType a; arcs.has(a);
	   @                     a.from.equals(n) || a.to.equals(n));
	   @*/
    public /*@ pure @*/ boolean unconnected(NodeType n) {
        Iterator arcIter = arcSet.iterator();
        while (arcIter.hasNext()) {
            Arc a = (Arc) arcIter.next();
            if (a.getSource().equals(n) || a.getTarget().equals(n)) {
                return false;
            }
        }
        return true;
    }

	

    /** Transpose this graph by inverting the direction of all its edges. */
    /*@ public normal_behavior
      @  assignable arcs;
      @  ensures nodes.equals(\old(nodes));
      @  ensures arcs.equals(flipAll(\old(arcs)));
      @*/
    public void transpose() {
        Iterator arciter = arcSet.iterator();
        while (arciter.hasNext()) {
            Arc a = (Arc) arciter.next();
            a.flip();
        }
    }

	

    private /*@ spec_public @*/ int time;

	 // for DFS and DFSVisit

    /* // !FIXME! -- write this in code
       public SearchableDigraph StronglyConnectedComponents() {
       DFS();
       SearchableDigraph trans = (SearchableDigraph) transpose();
       // do trans.DFS(); in decreasing order of finishing times.
       // add the vertices in the depthFirst forest formed by 
       //    trans.DFS() as a separate strongly connected componenet
       //    to the resulting graph by calling reachSet() on the vertices
       // add the appropriate arcs
       return trans;
       }
    */

    /*@ public normal_behavior
      @  assignable time, arcs, nodes;
      @  // !FIXME! complete this specification.
      @*/
    public void DFS() {
        Iterator nodes = nodeSet.iterator();
        while (nodes.hasNext()) {
            SearchableNode next = (SearchableNode) nodes.next();
            next.setColor(Color.WHITE);
            next.setPredecessor(null);
        }
        time = 0;
        while (nodes.hasNext()) {
            SearchableNode next = (SearchableNode) nodes.next();
            if (next.getColor() == Color.WHITE) {
                DFSVisit(next);
            }
        }
    }

	

    /*@ public normal_behavior
      @  requires u != null && u.color != null && u.color == Color.WHITE;
      @  assignable time, arcs, u.discoverTime, u.color,
      @             u.predecessor, u.finishTime;
      @  ensures u.color == Color.BLACK && u.discoverTime == \old(time + 1)
      @       && time > \old(time); // !FIXME! complete this specification.
      @*/
    public void DFSVisit(SearchableNode u) {
        u.setColor(Color.GRAY);
        time++;
        u.setDiscoverTime(time);
        Iterator arcs = arcSet.iterator();
        while (arcs.hasNext()) {
            Arc next = (Arc) arcs.next();
            if (next.getSource() == u) {
                if (((SearchableNode) next.getTarget()).getColor()
                    == Color.WHITE) {
                        ((SearchableNode) next.getTarget())
                            .setPredecessor((SearchableNode) next.getSource());
                    DFSVisit((SearchableNode) next.getTarget());
                }
            }
        }
        u.setColor(Color.BLACK);
        u.setFinishTime(time);
        time++;
    }

	

	 /*@  public normal_behavior
	   @   requires unconnected(n);
	   @   assignable nodes;
	   @   ensures nodes.equals(\old(nodes.remove(n)));
	   @*/
    public void removeNode(NodeType n) {
        nodeSet.remove(n);
    }

	

	 /*@  public normal_behavior
	   @   requires_redundantly inFrom != null && inTo != null;
	   @   requires nodes.has(inFrom) && nodes.has(inTo);
	   @   assignable arcs;
	   @   ensures arcs.equals(
	   @            \old(arcs).remove(new ArcType(inFrom, inTo)));
	   @*/
    public void removeArc(NodeType inFrom, NodeType inTo) {
        arcSet.remove(new Arc(inFrom, inTo));
    }


}
