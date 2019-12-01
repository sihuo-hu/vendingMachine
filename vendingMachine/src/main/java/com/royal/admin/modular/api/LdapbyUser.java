package com.royal.admin.modular.api;

import javax.naming.*;
import javax.naming.directory.*;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;

/**
 * Java通过Ldap操作AD的增删该查询
 *
 * @author guob
 */

public class LdapbyUser {

    static DirContext dc = null;

    //String root = "dc=example,dc=com"; // LDAP的根节点的DC
//    String root = "OU=Java,OU=dev,DC=test,DC=domain,DC=com";
    String root = "DC=para,DC=com";
    //String root = "dc=para,dc=com";

    /**
     * @param dn类似于"CN=RyanHanson,dc=example,dc=com"
     * @param employeeID是Ad的一个员工号属性
     * @param s
     * @param s1
     */

    public LdapbyUser(String s, String s1) {

        init();

//        add("Jackson");//添加节点

// delete("cn=张三,ou=Dev,dc=para,dc=com");//删除"ou=hi,dc=example,dc=com"节点
        //  delete("cn=ce76629c-9638-4ef9-b662-9c9638fef9bd,ou=Dev,dc=para,dc=com");
//	delete("CN=张悟,OU=Dev,DC=para,DC=com");
// renameEntry("ou=new,o=neworganization,dc=example,dc=com","ou=neworganizationalUnit,o=neworganization,dc=example,dc=com");//重命名节点"ou=new,o=neworganization,dc=example,dc=com"

        searchInformation("DC=para,DC=com", "", "sAMAccountName=Administrator");//遍历所有根节点
// searchInformation("CN=rtyuf,ou=Dev,dc=para,dc=com", "", "sAMAccountName=Administrator");
//		modifyInformation("cn=李四,ou=Dev,dc=para,dc=com", "123456");// 修改

// Ldapbyuserinfo("guob");//遍历指定节点的分节点

//		getSchema();
        close();

    }

    /**
     * Ldap连接
     *
     * @return LdapContext
     */

    public void init() {
        Hashtable env = new Hashtable();
        String LDAP_URL = "LDAP://172.16.46.168:389"; // LDAP访问地址
        String adminName = "AD\\Administrator"; // 注意用户名的写法：domain\User或zhangsan@para.com
        //String adminName = "zhangsan@para.com";
        String adminPassword = "dfl88@2018Admin"; // 密码
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, LDAP_URL);
        env.put(Context.SECURITY_AUTHENTICATION, "simple");//访问级别
        env.put(Context.SECURITY_PRINCIPAL, adminName);
        env.put(Context.SECURITY_CREDENTIALS, adminPassword);
        try {
            dc = new InitialDirContext(env);// 初始化上下文
            System.out.println("认证成功");// 这里可以改成异常抛出。
        } catch (AuthenticationException e) {
            System.out.println("认证失败");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("认证出错：" + e);
        }
    }

    /**
     * 添加
     */

    public void add(String newUserName) {
//		try {
//			BasicAttributes attrs = new BasicAttributes();
//			BasicAttribute objclassSet = new BasicAttribute("objectClass");
//			objclassSet.add("sAMAccountName");
//			//objclassSet.add("employeeID");
//			attrs.put(objclassSet);
//			//attrs.put("ou", newUserName);
//			attrs.put("sAMAccountName", newUserName);
//			//attrs.put("ou", "技术部");
//			//dc.createSubcontext("uid=" + newUserName + "," + root, attrs);
//			dc.createSubcontext("cn=" + newUserName + "," + root, attrs);
//			attrs.put("cn",newUserName);
//			attrs.put("sn",newUserName);
//		} catch (Exception e) {
//			e.printStackTrace();
//			System.out.println("Exception in add():" + e);
//		}


//        try {
//            Attributes attrs = new BasicAttributes(true);
//            attrs.put("objectClass", "user");
//            attrs.put("samAccountName", newUserName);
//            attrs.put("displayName", newUserName);
//            attrs.put("userPrincipalName", newUserName + "@test.domain.com");
//            dc.createSubcontext("CN=" + newUserName + "," + root, attrs);
//            System.out.println("新增AD域用户成功:" + newUserName);
//        } catch (Exception e) {
//            e.printStackTrace();
//            System.out.println("新增AD域用户失败:" + newUserName);
//        }

        try {
            Attributes attrs = new BasicAttributes(true);
            attrs.put("objectClass", "user");
            attrs.put("samAccountName", newUserName);
            attrs.put("userPrincipalName", newUserName + "@para.com");
            attrs.put("telephoneNumber", "15880277368");
            attrs.put("displayName", "显示名称");
            attrs.put("description", "描述");
            attrs.put("mail", newUserName + "@163.com");
            attrs.put("givenName", "名字");
            attrs.put("name", "newUserName");

            attrs.put("cn", newUserName);
            //用户的姓
            attrs.put("sn", newUserName);

            dc.createSubcontext("CN=" + newUserName + "," + root, attrs);
            System.out.println("新增AD域用户成功:" + newUserName);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("新增AD域用户失败:" + newUserName);
        }
    }


    /**
     * 删除
     *
     * @param dn
     */

    public void delete(String dn) {

        try {

            dc.destroySubcontext(dn);

        } catch (Exception e) {

            e.printStackTrace();

            System.out.println("Exception in delete():" + e);

        }

    }

    /**
     * 重命名节点
     *
     * @param oldDN
     * @param newDN
     * @return
     */

    public boolean renameEntry(String oldDN, String newDN) {

        try {

            dc.rename(oldDN, newDN);

            return true;

        } catch (NamingException ne) {

            System.err.println("Error: " + ne.getMessage());

            return false;

        }

    }

    /**
     * 修改
     *
     * @return
     */

    public boolean modifyInformation(String dn, String employeeID) {
        try {
            System.out.println("updating...\n");
            ModificationItem[] mods = new ModificationItem[1];
            /* 修改属性 */
// Attribute attr0 = new BasicAttribute("employeeID", "W20110972");
// mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr0);
            /* 删除属性 */
// Attribute attr0 = new BasicAttribute("description", "陈轶");
// mods[0] = new ModificationItem(DirContext.REMOVE_ATTRIBUTE,attr0);

            /* 添加属性 */

            Attribute attr0 = new BasicAttribute("employeeID", employeeID);

            mods[0] = new ModificationItem(DirContext.ADD_ATTRIBUTE, attr0);

            /* 修改属性 */
            System.out.println();
            dc.modifyAttributes(dn, mods);

            return true;

        } catch (NamingException e) {

            e.printStackTrace();

            System.err.println("Error: " + e.getMessage());

            return false;

        }

    }

    /**
     * 关闭Ldap连接
     */

    public void close() {

        if (dc != null) {

            try {

                dc.close();

            } catch (NamingException e) {

                System.out.println("NamingException in close():" + e);

            }

        }

    }

    /**
     * @param base   ：根节点(在这里是"dc=example,dc=com")
     * @param scope  ：搜索范围,分为"base"(本节点),"one"(单层),""(遍历)
     * @param filter ：指定子节点(格式为"(objectclass=*)",*是指全部，你也可以指定某一特定类型的树节点)
     */

    public void searchInformation(String base, String scope, String filter) {
        SearchControls sc = new SearchControls();
        if (scope.equals("base")) {
            sc.setSearchScope(SearchControls.OBJECT_SCOPE);
        } else if (scope.equals("one")) {
            sc.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        } else {
            sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
        }
        NamingEnumeration ne = null;
        try {
            ne = dc.search(base, filter, sc);
// Use the NamingEnumeration object to cycle through
// the result set.
            while (ne.hasMore()) {
                System.out.println();
                SearchResult sr = (SearchResult) ne.next();
                String name = sr.getName();
                if (base != null && !base.equals("")) {
                    System.out.println("entry: " + name + "," + base);
                } else {
                    System.out.println("entry: " + name);
                }
                Attributes at = sr.getAttributes();
                NamingEnumeration ane = at.getAll();
                while (ane.hasMore()) {
                    Attribute attr = (Attribute) ane.next();
                    String attrType = attr.getID();
                    System.out.println(attrType);
                    NamingEnumeration values = attr.getAll();
// Another NamingEnumeration object, this time
// to iterate through attribute values.
                    while (values.hasMore()) {
                        Object oneVal = values.nextElement();
                        if (oneVal instanceof String) {
                            System.out.println(attrType + ": " + (String) oneVal + "-----");
                        } else {
                            System.out.println(attrType + ": " + new String((byte[]) oneVal));
                        }
                    }
                }
            }
        } catch (PartialResultException nex) {

        } catch (Exception nex) {
            System.err.println("Error: " + nex.getMessage());
            nex.printStackTrace();
        }
    }

    public String search(String identity) {
        System.out.println("=================================");
        Properties env = new Properties();
        String adminName = "para\\Administrator";//username@domain
        String adminPassword = "password";//password
        String ldapURL = "LDAP://192.168.220.134:389";//ip:port

        String DN = new String();

        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");//"none","simple","strong"
        env.put(Context.SECURITY_PRINCIPAL, adminName);
        env.put(Context.SECURITY_CREDENTIALS, adminPassword);
        env.put(Context.PROVIDER_URL, ldapURL);
        try {
            LdapContext ctx = new InitialLdapContext(env, null);
            SearchControls searchCtls = new SearchControls();
            searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
//            String searchFilter = "(&(objectCategory=person)(objectClass=user)(name=Cynthia))";//CN=rtyuf,ou=Dev,dc=para,dc=com
            String searchFilter = "(&(objectCategory=person)(objectClass=user)(name="+identity+"))";//CN=rtyuf,ou=Dev,dc=para,dc=com
            //String searchFilter = "(&(objectClass=top)(objectClass=person)(objectClass=organizationalPerson)(objectClass=user))";
            //String searchFilter = "(&(objectCategory=person)(objectClass=user)(name=rtyuf))";
            String searchBase = "DC=para,DC=com";
            //String searchBase = "CN=rtyuf,ou=Dev,dc=para,dc=com";//CN=rtyuf,
            //[CN=erq,ou=Dev,dc=para,dc=com]
            String returnedAtts[] = {"memberOf"};
            searchCtls.setReturningAttributes(returnedAtts);
            NamingEnumeration<SearchResult> answer = ctx.search(searchBase, searchFilter, searchCtls);
            System.out.println();
            while (answer.hasMoreElements()) {
                System.out.println("ok");
                SearchResult sr = (SearchResult) answer.next();
                System.out.println(sr.getName());
                DN = sr.getName() + ","+root;
            }
            ctx.close();
        } catch (NamingException e) {
            e.printStackTrace();
            System.err.println("Problem searching directory: " + e);
        }
        return DN;
    }

    /**
     * 查询
     *
     * @throws NamingException
     */
    public void Ldapbyuserinfo(String userName) {
// Create the search controls
        SearchControls searchCtls = new SearchControls();
// Specify the search scope
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
// specify the LDAP search filter
        String searchFilter = "sAMAccountName=" + userName;
// Specify the Base for the search 搜索域节点
        //String searchBase = "DC=example,DC=COM";
        String searchBase = "DC=para,DC=COM";
        int totalResults = 0;
        String returnedAtts[] = {"url", "whenChanged", "employeeID", "name",
                "userPrincipalName", "physicalDeliveryOfficeName",
                "departmentNumber", "telephoneNumber", "homePhone", "mobile",
                "department", "sAMAccountName", "whenChanged", "mail"}; // 定制返回属性
        searchCtls.setReturningAttributes(returnedAtts); // 设置返回属性集
// searchCtls.setReturningAttributes(null); // 不定制属性，将返回所有的属性集
        try {
            NamingEnumeration answer = dc.search(searchBase, searchFilter, searchCtls);
            if (answer == null || answer.equals(null)) {
                System.out.println("answer is null");
            } else {
                System.out.println("answer not null");
            }
            while (answer.hasMoreElements()) {
                SearchResult sr = (SearchResult) answer.next();
                System.out.println("************************************************");
                System.out.println("getname=" + sr.getName());
                Attributes Attrs = sr.getAttributes();
                if (Attrs != null) {
                    try {
                        for (NamingEnumeration ne = Attrs.getAll(); ne.hasMore(); ) {
                            Attribute Attr = (Attribute) ne.next();
                            System.out.println("AttributeID=" + Attr.getID().toString());// 读取属性值
                            for (NamingEnumeration e = Attr.getAll(); e
                                    .hasMore(); totalResults++) {
                                String user = e.next().toString(); // 接受循环遍历读取的userPrincipalName用户属性
                                System.out.println(user);
                            }
// System.out.println(" ---------------");
// // 读取属性值
// Enumeration values = Attr.getAll();
// if (values != null) { // 迭代
// while (values.hasMoreElements()) {
// System.out.println(" 2AttributeValues="
// + values.nextElement());
// }
// }
// System.out.println(" ---------------");
                        }
                    } catch (NamingException e) {
                        System.err.println("Throw Exception : " + e);
                    }
                }
            }
            System.out.println("Number: " + totalResults);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Throw Exception : " + e);
        }
    }


    public Set<String> getSchema() {
        Set<String> set = new HashSet<String>();
        String base = "DC=para,DC=com";
        String scope = "";
        String filter = "sAMAccountName=Administrator";
        SearchControls sc = new SearchControls();
        if (scope.equals("base")) {
            sc.setSearchScope(SearchControls.OBJECT_SCOPE);
        } else if (scope.equals("one")) {
            sc.setSearchScope(SearchControls.ONELEVEL_SCOPE);
        } else {
            sc.setSearchScope(SearchControls.SUBTREE_SCOPE);
        }
        NamingEnumeration ne = null;
        try {
            ne = dc.search(base, filter, sc);
            System.out.println("fdafdaf");
            while (ne.hasMore()) {
                SearchResult sr = (SearchResult) ne.next();
                Attributes at = sr.getAttributes();
                NamingEnumeration ane = at.getAll();
                while (ane.hasMore()) {
                    Attribute attr = (Attribute) ane.next();
                    String attrType = attr.getID();
                    set.add(attrType);
                }
            }
            System.out.println("fdaer" + set);
        } catch (PartialResultException nex) {

        } catch (Exception nex) {
            System.err.println("Error: " + nex.getMessage());
            nex.printStackTrace();
        }
        System.out.println(set);
        System.out.println(set.size());
        return set;
    }


    /**
     * 主函数用于测试
     *
     * @param args
     * @throws NamingException
     */
    public static void main(String[] args) throws NamingException {
        new LdapbyUser("", "");
//       new LdapbyUser("","").add("Cynthia");

        String str = new LdapbyUser("", "").search("张三");
        System.out.println(str);
//		String baseDN = "dc=ad,dc=test";
//		String query = "(&(objectClass=top)(objectClass=person)(objectClass=organizationalPerson)(objectClass=user))";
//		SearchControls searchControls = new SearchControls();
//		NamingEnumeration<SearchResult> results = dc.search(baseDN, query, searchControls);
    }

}
