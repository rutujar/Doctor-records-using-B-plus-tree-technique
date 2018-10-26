#include<iostream.h>
#include<stdio.h>
#include<fstream.h>
#include<stdlib.h>
#include<string.h>

class node
{
    public:
    int a[4];
    node * next[4];
    node * parent;
    int size;
    node();
};

class linkleaf
{
    public:
    node * data;
    linkleaf *next;
    linkleaf();
};

linkleaf :: linkleaf()
{
  next = NULL;
}

node :: node()
{
      for(int i = 0; i < 4; i++)
	  next[i] = NULL;
      parent = NULL;
      size = 0;
}

class btree
{
    node * root;
    linkleaf *head;
    int flag;
    public:
    node* findLeaf(int key,int &level);
    void updateKey(node *p,node *c,int newKey);
    void search(int key);
    void insert(int key);
    void insertIntoNode(node *n,int key,node *address);
    void promote(node *n,int key,node *address);
    node* split(node *n);
    void traverse(node  *ptr);
    void connectLeaf(node *n,node *newptr);
    void traverseleaf();
    btree();
};

void btree :: traverseleaf()
{
    linkleaf * ptr = head;
    int i;
    while(ptr)
    {
	  for(i = 0; i < ptr -> data -> size; i++)
		cout<<ptr -> data -> a[i]<<"    ";
	  cout<<endl;
	  ptr = ptr ->next;
    }
}

void btree :: connectLeaf(node *n,node *newptr)
{
    linkleaf *ptr = head,*prevptr = NULL,*p;
    while(ptr)
    {
	if(ptr-> data == n)
	    break;
	prevptr = ptr;
	ptr = ptr ->next;
    }
    if( ptr == NULL)
    {
	    cout<<"Unexpected Error!!";
	    exit(0);
    }

    if( ptr == head)
    {
	p = new linkleaf;
	p -> next = head;
	head = p;
	p->data = newptr;
    }
    else
    {
	p = new linkleaf;
	p-> next = ptr;
	prevptr -> next = p;
	p->data = newptr;
    }
   
}

void btree :: traverse(node *ptr)
{
      if(ptr == NULL)
	return;
      for(int i = 0; i < ptr->size; i++)
	    cout<<ptr->a[i]<<"    ";
      cout<<endl;
      for( i = 0;  i < ptr->size;i++)
	  traverse(ptr->next[i]);
    
}

btree :: btree()
{
    root = NULL;
    head = NULL;
}

node* btree :: findLeaf(int key,int &level)
{
    node *ptr = root;
    node *prevptr = NULL;
    level = 0;
    int i;
    while(ptr)
    {
	 i = 0;
	  level++;
	 while(i < ptr -> size-1 && key > ptr -> a[i])
		i++;
	  prevptr = ptr;
	 ptr = ptr -> next[i];
    }
    return prevptr;
}

node* btree :: split(node *n)
{
    int midpoint = (n -> size+1)/2;
    int newsize = n->size - midpoint;
    node *newptr = new node;
    node *child;
    newptr->parent = n -> parent;
    int i;
    for(i = 0; i < midpoint; i++)
    {
	newptr->a[i] = n->a[i];
	newptr ->next[i] = n->next[i];
	n->a[i] = n->a[i+midpoint];
	n->next[i] = n->next[i+midpoint];
    }
    n->size = midpoint;
    newptr -> size = newsize;
    for( i = 0; i < n->size; i++)
    {
	child = n->next[i];
	if(child!= NULL)
	child -> parent = n;
    }
    for( i  = 0; i < newptr -> size; i++)
    {
	child = newptr -> next[i];
	if(child!= NULL)
	child -> parent = newptr;
    }
    return newptr;
}

void btree :: updateKey(node *parent,node *child,int newkey)
{
      if( parent == NULL)
	return;
      if(parent->size == 0)
	    return;
      int oldkey = child->a[child->size-2];
      
      for(int i = 0; i < parent->size;i++)
	  if(parent->a[i] == oldkey)
	  {  parent->a[i] = newkey;
	     parent->next[i] = child;
	  }
}

void btree :: insertIntoNode(node *n,int key,node *address)
{
    int i;
    if( n == NULL)
	return;
    
    for(i = 0; i < n->size; i++)
	if(n->a[i] == key)
	    return;

    i = n->size-1;
    while(i >= 0 && n -> a[i] > key)
    {
	n->a[i+1] = n->a[i];
	n->next[i+1] = n->next[i];
	i--;
    }
    i++;
    n->a[i] = key;
    n->next[i] = address;
    n->size++;
    if( i == n->size-1)
	updateKey(n->parent,n,key);
}

void btree :: promote(node *n,int key,node *address)
{
    if( n == NULL)
	return;

    if(n -> size < 4)
    {
	insertIntoNode(n,key,address);
	return;
    }

    if( n == root)
    {
	root = new node;
	n->parent = root;
    }
    node *newptr = split(n);
    node *t;
    if(key < n->a[0])
	  t = newptr;
    else
	  t = n;
    insertIntoNode(t,key,address);
    if(!flag)
    {	  connectLeaf(n,newptr);flag = 1;}
    promote(n->parent,n->a[n->size-1],n);
    promote(newptr->parent,newptr->a[newptr->size-1],newptr);
    
}

void btree :: insert(int key)
{
    flag = 0;
    if( root == NULL)
    {
	  root = new node;
	  root->a[root->size] = key;
	  root->size++;
	  head = new linkleaf;
	  head -> data = root;
	  return;
    }
    int level;
    node *leaf = findLeaf(key,level);
    int i;
    for(i = 0; i < leaf->size; i++)
	if(leaf -> a[i] == key)
	{
		cout<<"The Key to be inserted already exists"<<endl;
		return;
	}
    promote(leaf,key,NULL);
     cout<<"---------------\n";
      traverse(root);
      cout<<"----------------\n";
}
    
void btree :: search(int key)
{
       if(root == NULL)
      {	  cout<<"The tree Does not exist"<<endl;
	  return;
      }
      int level;
      node *leaf = findLeaf(key,level);
      int flag = 0;
      for(int i = 0; i < leaf ->size; i++)
	  if(leaf->a[i] == key)
	  {
	      flag = 1;
	      cout<<"The Key "<<key<<" Exists in the B-Tree at the level"<<level<<endl;
	  }
      if(!flag)
	  cout<<"The Key Searched for was not found"<<endl;
}

int main()
{
    btree b;
    int choice = 1,key;
    while(choice <=3)
    {
	    cout<<"1.Insert a Key\n";
	    cout<<"2.Search a key\n";
	    cout<<"3.Traverse Leaf\n";
	    cout<<"4.Exit\n";
	    cin>>choice;
	    switch(choice)
	    {
		  case 1: cout<<"Enter The Key to be inserted in B-Tree\n";
			  cin>>key;
			  b.insert(key);
			  break;
		  case 2: cout<<"Enter The key to be searched\n";
			  cin>>key;
			  b.search(key);
			  break;
		  case 3: b. traverseleaf();
			  break;
	    }
    }
    return 0;
}
