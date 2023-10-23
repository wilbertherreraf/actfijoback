package gob.gamo.activosf.app.treeorg;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public interface OrgHierarchyInterface<T> {
    public boolean isEmpty(); /* Returns true if the organization is empty. */

    public int size(); /* Returns the number of employees in the organization */

    public int level(int id) throws IllegalIDException; /* Returns the level of the employee with ID=id */

    public int maxLevel();

    public void hireOwner(Node<T> owner) throws NotEmptyException;

    public void hireOwner(int id) throws NotEmptyException; /*
                                                             * Adds the first employee of the organization, which we
                                                             * call the owner. There is only one owner in an org who
                                                             * cannot be deleted.
                                                             */

    public void addNewChild(Node<T> suNode, int bossid) throws IllegalIDException;

    public void addNewChild(int id, int bossid) throws IllegalIDException; /*
                                                                             * Adds a new employee whose ID is id. This
                                                                             * employee will work under an existing
                                                                             * employee whose ID is bossid (note that
                                                                             * this automatically decides the level of
                                                                             * id, it is one more than that of bossid).
                                                                             */

    public void deleteWithNewBoss(int id, Node<T> manage, int bossid) throws IllegalIDException;

    public void deleteNode(int id) throws IllegalIDException; /*
                                                                 * Deletes an employee who does not manage any other
                                                                 * employees. Note that this can not be the owner. If it
                                                                 * is the owner, throw the IllegalIDException.
                                                                 */

    public void replaceWithOtherNode(int id, int manageid) throws IllegalIDException; /*
                                                                               * Deletes an employee (id) who might
                                                                               * manage other employees. Manageid is
                                                                               * another employee who works at the same
                                                                               * level as id. All employees working
                                                                               * under id will now work under manageid.
                                                                               * Note that this can not be the owner. If
                                                                               * it is the owner, throw the
                                                                               * IllegalIDException.
                                                                               */

    public void updateParent(int id, int bossid);

    public int boss(int id) throws IllegalIDException; /*
                                                        * Returns the id of the immediate boss, the employee. Output -1
                                                        * if id is the owner’s ID
                                                        */

    public int lowestCommonBoss(int id1, int id2) throws IllegalIDException; /*
                                                                              * Returns the ID of the employee A who is
                                                                              * a boss of both id1 and id2, and among
                                                                              * all such persons has the largest level.
                                                                              * In other words, we want to find the
                                                                              * common boss who is lowest in the
                                                                              * hierarchy in the company. If one of the
                                                                              * input ids is the owner, output -1
                                                                              */

    public String toStringNode(int id) throws IllegalIDException; /*
                                                                   * This method returns a string that contains the IDs
                                                                   * of
                                                                   * all the employees of the organisation rooted at id.
                                                                   * It
                                                                   * should contain the employees level-wise. So first
                                                                   * it
                                                                   * should have id, then ids of all the employees
                                                                   * directly
                                                                   * under id, and then all employees directly these
                                                                   * employees and so on. Make sure that in the string
                                                                   * levels are comma separated and employees inside a
                                                                   * level
                                                                   * are space separated. Among employees at the same
                                                                   * level,
                                                                   * the output should be sorted in increasing order of
                                                                   * the
                                                                   * ids.
                                                                   */

    public Node<T> getRoot();

    public Node<T> searchNode(int id) throws IllegalIDException;

    public Node<T> searchInTree(int id);

    public LinkedHashMap<Integer, Node<T>> returnChildrens(Node<T> root);

    public LinkedList<Node<T>> getbylevel(Node<T> root, int level);

    public List<String> nivls(Node<T> root);

    public Map<Integer, LinkedList<Node<T>>> treeByLevels(Node<T> root);

    public Node<T> returnRoot();
}
