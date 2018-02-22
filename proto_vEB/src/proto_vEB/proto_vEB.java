package proto_vEB;
/*
Proto Van Emde Boas Tree data structure.
Complexity:    O(log log u) for member
               O(log u) for insert, delete, min, max, extractMin
               O(log u log log u) for successor, Predecessor
*/
public class proto_vEB {
	public int u;
	public int[] A;
	public proto_vEB[] cluster;
	public proto_vEB summary;
	public proto_vEB(int size) {
		if (size == 2) {
			u = size;
			A = new int[2];
		} else {
			u = size;
			cluster = new proto_vEB[(int)Math.sqrt(u)];
			for (int i = 0; i < (int)Math.sqrt(u); i++) {
				cluster[i] = new proto_vEB((int)Math.sqrt(u));
			}
			summary = new proto_vEB((int)Math.sqrt(u));
		}
	}
	public boolean member (int k) {
		if (k >= this.u) return false;
		if (this.u == 2) {
			return this.A[k] == 1 ? true:false;
		} else {
			int high = k / (int)Math.sqrt(this.u);
			int low = k % (int)Math.sqrt(this.u);
			return this.cluster[high].member(low);
		}
	}
	public void insert (int k) {
		if (this.u == 2) {
			this.A[k] = 1;
		} else {
			int high = k / (int)Math.sqrt(this.u);
			int low = k % (int)Math.sqrt(this.u);
			this.cluster[high].insert(low);
			this.summary.insert(high);
		}
	}
	public void delete (int k) {
		if (this.u == 2) {
			this.A[k] = 0;
		} else {
			int high = k / (int)Math.sqrt(this.u);
			int low = k % (int)Math.sqrt(this.u);
			this.cluster[high].delete(low);
			boolean c = !isEmpty(this.cluster[high]);
			if (!c) {
				this.summary.delete(high);
			}
		}
	}
	public Integer successor (int k) {
		if(this.u == 2) {
			if (k==0 && this.A[1]==1) {
				return 1;
			} else return null;
		} 
		else {
			int high = k / (int)Math.sqrt(this.u);
			int low = k % (int)Math.sqrt(this.u);
			Integer offset = this.cluster[high].successor(low);
			if ( offset != null) {
				return (high) * this.cluster.length + offset;
			} else {
				Integer succ_cluster = this.summary.successor (high);
				if (succ_cluster == null) {
					return null;
				} else {
					return succ_cluster * this.cluster.length + this.cluster[succ_cluster].min();
				}
			}
		}
	}
	public Integer predecessor (int k) {
		if(this.u == 2) {
			if (k==1 && this.A[0]==1) {
				return 0;
			} else return null;
		} 
		else {
			int high = k / (int)Math.sqrt(this.u);
			int low = k % (int)Math.sqrt(this.u);
			Integer offset = this.cluster[high].predecessor(low);
			if (offset != null) {
				return (high) * this.cluster.length + offset;
			} else {
				Integer succ_cluster = this.summary.predecessor (high);
				if (succ_cluster == null) {
					return null;
				} else {
					return succ_cluster * this.cluster.length + this.cluster[succ_cluster].max();
				}
			}
		}
	}
	public Integer min () {
		if (this.u == 2) {
			return this.A[0] == 1? 0: this.A[1] == 1? 1: null;
		} else {
			Integer min = this.summary.min();
			if (min == null) {
				return null;
			} else {
				Integer offset = this.cluster[min].min ();
				return min * this.cluster.length + offset;
			}
		}
	}
	public Integer max () {
		if (this.u == 2) {
			return this.A[1] == 1 ? 1: this.A[0] == 1 ? 0: null;
		} else {
			Integer max = this.summary.max();
			if (max == null) {
				return null;
			} else {
				Integer offset = this.cluster[max].max ();
				return max * this.cluster.length + offset;
			}
		}
	}
	public Integer extractMin () {
		Integer min = this.min();
		this.delete(min);
		return min;
	}
	private boolean isEmpty(proto_vEB root) {
		if (root.u == 2) {
			return (root.A[0] == 0 && root.A[1] == 0) ? true : false;
		}
		for (int i = 0; i < root.cluster.length; i++) {
			if (!isEmpty(root.cluster[i])) {
				return false;
			}
		}
		return true;
	}
	public static void main(String[] args) {
		proto_vEB root = new proto_vEB(16);
		System.out.println("A[0] = " + root.cluster[0].cluster[0].A[0]);
		System.out.println("A[1] = " + root.cluster[0].cluster[0].A[1]);
		System.out.println("summary[0] = " + root.summary.cluster[0].A[0]);
		System.out.println("=-=-=-=-=-=-=-=-=");
		System.out.println("Insert 0 and 1.");
		root.insert(1);
		root.insert(0);
		System.out.println("A[0] = " + root.cluster[0].cluster[0].A[0]);
		System.out.println("A[1] = " + root.cluster[0].cluster[0].A[1]);
		System.out.println("summary[0] = " + root.summary.cluster[0].A[0]);
		System.out.println("=-=-=-=-=-=-=-=-=");
		System.out.println("Delete 1.");
		root.delete(1);
		System.out.println("A[0] = " + root.cluster[0].cluster[0].A[0]);
		System.out.println("A[1] = " + root.cluster[0].cluster[0].A[1]);
		System.out.println("summary[0] = " + root.summary.cluster[0].A[0]);
		System.out.println("=-=-=-=-=-=-=-=-=");
		System.out.println("Delete 0.");
		root.delete(0);
		System.out.println("A[0] = " + root.cluster[0].cluster[0].A[0]);
		System.out.println("A[1] = " + root.cluster[0].cluster[0].A[1]);
		System.out.println("summary[0] = " + root.summary.cluster[0].A[0]);
//		System.out.println("A[11] = " + root.cluster[2].cluster[1].A[1]);
//		root.insert(11);
//		System.out.println("A[11] = " + root.cluster[2].cluster[1].A[1]);
	}
}

