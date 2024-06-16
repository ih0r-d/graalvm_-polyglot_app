import numpy as np
from scipy.optimize import linprog


def define_objective():
    # Define a specific objective function
    c = [-1, -2, -3, -4, -5]  # Example coefficients for maximizing the objective function
    print(f"Objective function coefficients: {c}")
    return c


def define_constraints():
    # Define specific inequality and equality constraints
    a = [
        [2, 1, 1, 0, 0],
        [1, 3, 0, 1, 0],
        [0, 2, 0, 0, 1]
    ]
    b = [20, 30, 15]

    a_eq = [
        [1, 1, 1, 1, 1]
    ]
    b_eq = [40]

    print(f"Inequality constraints (A <= b): A = {a}, b = {b}")
    print(f"Equality constraints (A_eq = b_eq): A_eq = {a_eq}, b_eq = {b_eq}")

    return a, b, a_eq, b_eq


def define_bounds(num_variables):
    # Define specific bounds for each variable
    x_bounds = [(0, None) for _ in range(num_variables)]
    print(f"Variable bounds: {x_bounds}")
    return x_bounds


def solve_linear_program(c, a, b, a_eq, b_eq, x_bounds):
    try:
        # Solve the linear programming problem using the HiGHS solver
        result = linprog(c, A_ub=a, b_ub=b, A_eq=a_eq, b_eq=b_eq, bounds=x_bounds, method='highs')
        return result
    except Exception as e:
        print(f"An error occurred: {e}")
        return None


def format_results(result):
    if result is not None and result.success:
        # Format the result as a dictionary
        res = {
            "optimal_value": result.fun,
            "optimal_solution": result.x.tolist(),
            "success": result.success,
            "message": result.message
        }
    else:
        res = {
            "success": False,
            "message": "Optimization failed."
        }
    return res


def main():
    c = define_objective()
    a, b, a_eq, b_eq = define_constraints()
    num_variables = len(c)
    x_bounds = define_bounds(num_variables)
    result = solve_linear_program(c, a, b, a_eq, b_eq, x_bounds)
    return format_results(result)


if __name__ == "__main__":
    results_dict = main()
    print(results_dict)
