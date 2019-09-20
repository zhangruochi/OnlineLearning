import numpy as np

def rad(a,b):
    return np.arccos( np.dot(a,b) / (np.sqrt(np.dot(a,a)) * np.sqrt(np.dot(b,b))))


# print(rad([3,4],[1,-1]))



def inner_product(vector1,A,vector2):
    return vector1.T @ A @ vector2

def distance(vector1,A,vector2):
    diff_vec = vector1 - vector2
    return np.sqrt(inner_product(diff_vec,A,diff_vec))


def length(vector,A):
    return np.sqrt(inner_product(vector,A,vector))


def angle(vector1,A,vector2):
    return np.arccos( inner_product(vector1,A,vector2) / (np.sqrt(inner_product(vector1,A,vector1)) * np.sqrt(inner_product(vector2,A,vector2))) )



def projection_matrix2D(b):
    return (b @ b.T) / np.dot(b.T,b)


def project2D(projection_matrix,vector):
    return projection_matrix @ vector



def projection_matrix3D(b):
    return b @ np.linalg.inv(b.T @ b) @ b.T

def get_lambda_3D(b,vector):
    return np.linalg.inv(b.T @ b) @ b.T @ vector


def project3D(projection_matrix,vector):
    return projection_matrix @ vector

def get_rank(matrix):
    return np.linalg.matrix_rank(matrix)




vector = np.array([12,0,0])
b = np.array([[1,0],[1,1],[1,2]])

a = project3D(projection_matrix3D(b),vector)



second_b = np.array([-10*np.sqrt(6),-4*np.sqrt(6),2*np.sqrt(6)])
projection_matrix = projection_matrix2D(second_b)





# vector = np.array([6,0,0])
# b = np.array([[1,0],[1,1],[1,2]])

# print(get_rank(projection_matrix3D(b)))

# print(project3D(projection_matrix3D(b),vector))

# print(get_lambda_3D(b,vector))



# ori = np.array([1,1,1])
# pro = 1/9 * np.array([5,10,10])
# print(distance(ori,np.eye(3),pro))


# projection_matrix = 1/25 * np.array([[9,0,12],[0,0,0],[12,0,16]])
# vector = np.array([1,1,1])
# print(project(projection_matrix,vector))





# b = np.array([[1],[2],[2]])
# print(projection_matrix(b))


# vector = np.array([[1],[-1],[3]])
# A = np.array([[2,1,0],[1,2,-1],[0,-1,2]])

# print(length(vector,A))


# vector1 = np.array([[1/2],[-1],[-1/2]])
# vector2 = np.array([[0],[1],[0]])
# A = np.array([[2,1,0],[1,2,-1],[0,-1,2]])

# print(distance(vector1,A,vector2))


# vector = np.array([[-1],[1]])
# A = 1/2 * np.array([[5,-1],[-1,5]])
# print(length(vector,A))


# A = np.array([[2,1,0],[1,2,-1],[0,-1,2]])
# vector1 = np.array([[4],[2],[1]])
# vector2 = np.array([[0],[1],[0]])
# print(distance(vector1,A,vector2))


# A = np.array([[2,-1],[-1,4]])
# vector1 = np.array([[1],[1]])
# vector2 = np.array([[-1],[1]])

# print(angle(vector1,A,vector2))


# A = np.array([[1,-1/2],[-1/2,5]])
# vector1 = np.array([[0],[-1]])
# vector2 = np.array([[1],[1]])

# print(angle(vector1,A,vector2))


# A = np.array([[2,1],[1,4]])
# vector1 = np.array([[2],[2]])
# vector2 = np.array([[-2],[-2]])

# print(angle(vector1,A,vector2))



# A = np.array([[1,0],[0,5]])
# vector1 = np.array([[1],[1]])
# vector2 = np.array([[1],[-1]])

# print(angle(vector1,A,vector2))



# A = np.array([[1,0,0],[0,2,-1],[0,-1,3]])
# vector1 = np.array([[1],[1],[1]])
# vector2 = np.array([[2],[-1],[0]])

# print(angle(vector1,A,vector2))

