!pip install tensorflow-graphics
!pip install trimesh

import numpy as np
import tensorflow as tf
import trimesh

import tensorflow_graphics.geometry.transformation as tfg_transformation
from tensorflow_graphics.notebooks import threejs_visualization

!wget -N https://cdn.discordapp.com/attachments/716742625515798610/801130786442313788/bs_smile.obj

mesh = trimesh.load("bs_smile.obj")
mesh = {"vertices": mesh.vertices, "faces": mesh.faces}

_ = threejs_visualization.triangular_mesh_renderer(mesh, width=800, height=800)

axis = np.array((0., 1., 0.))
angle = np.array((np.pi / 4.,))

mesh["vertices"] = tfg_transformation.axis_angle.rotate(mesh["vertices"], axis,
                                                        angle).numpy()

_ = threejs_visualization.triangular_mesh_renderer(mesh, width=800, height=800)
