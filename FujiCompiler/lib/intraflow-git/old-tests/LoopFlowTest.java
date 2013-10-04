package test;

import java.util.*;

public class LoopFlowTest {


	void m1() {
		
		int c = 0;
		int b = 0;
		int e = 0;
		entries: while (e < 100) {
			returns: while (b < 100) {
				b++;
				if (b == 10) {
					continue;
				}
				int a = 0;
				while (a < 100) {
					a++;
					if (a > 10) {
						c = a; // should not be dead
						break returns;
					}
				}
				if (c > 10) {
					continue entries;
				}
			}
		}
	}


private HashMap<Object, Object> subroutines = new HashMap<Object, Object>();

 private void splitPhiBlocks() {
    Iterator entries = subroutines.values().iterator();
    ENTRIES:
      while(entries.hasNext()){
        Subroutine entrySub = (Subroutine)entries.next();
        Block block = entrySub.entry();
        Subroutine returnSub = null;
        Block returnSubCaller = null;
        Iterator returns = subroutines.values().iterator();
        RETURNS:
          while(returns.hasNext()){
            returnSub = (Subroutine)returns.next();
            if(returnSub == entrySub) {
              continue ;
            }
            Iterator paths = returnSub.paths().iterator();
            while(paths.hasNext()){
              Block[] path = (Block[])paths.next();
              if(block == path[1]) {
                returnSubCaller = path[0];
                break RETURNS;
              }
            }
          }
        if(returnSubCaller == null) {
          continue ENTRIES;
        }
		/*
        if(DEBUG) {
          System.out.println("" + block + " is both an entry and a return target");
        }
        int traceIndex = trace.indexOf(block);
        Tree tree;
        Block newEntry = newBlock();
        trace.add(traceIndex, newEntry);
        tree = new Tree(newEntry, returnSub.exit().tree().stack());
        newEntry.setTree(tree);
        tree.addInstruction(new Instruction(Opcode.opcx_goto, block.label()));
        addEdge(newEntry, block);
        Iterator paths = entrySub.paths().iterator();
        while(paths.hasNext()){
          Block[] path = (Block[])paths.next();
          removeEdge(path[0], block);
          addEdge(path[0], newEntry);
          path[0].visit(new ReplaceTarget(block, newEntry));
        }
        setSubEntry(entrySub, newEntry);
        Block newTarget = newBlock();
        trace.add(traceIndex, newTarget);
        tree = new Tree(newTarget, returnSub.exit().tree().stack());
        newTarget.setTree(tree);
        tree.addInstruction(new Instruction(Opcode.opcx_goto, block.label()));
        returnSub.exit().visit(new ReplaceTarget(block, newTarget));
        ((JsrStmt)returnSubCaller.tree().lastStmt()).setFollow(newTarget);
        addEdge(newTarget, block);
        addEdge(returnSub.exit(), newTarget);
        removeEdge(returnSub.exit(), block);
        JumpStmt entryJump = (JumpStmt)newEntry.tree().lastStmt();
        JumpStmt targetJump = (JumpStmt)newTarget.tree().lastStmt();
        Iterator e = handlers.values().iterator();
        while(e.hasNext()){
          Handler handler = (Handler)e.next();
          if(handler.protectedBlocks().contains(block)) {
            Assert.isTrue(succs(block).contains(handler.catchBlock()));
            handler.protectedBlocks().add(newEntry);
            addEdge(newEntry, handler.catchBlock());
            entryJump.catchTargets().add(handler.catchBlock());
            handler.protectedBlocks().add(newTarget);
            addEdge(newTarget, handler.catchBlock());
            targetJump.catchTargets().add(handler.catchBlock());
          }
        }
		*/
      }
  }

}

class Subroutine {
	public Block entry() {
		return new Block();
	}
	public Collection paths() {
		return new ArrayList();
	}
}
class Block {
}

